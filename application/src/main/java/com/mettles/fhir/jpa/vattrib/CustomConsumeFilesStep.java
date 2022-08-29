package com.mettles.fhir.jpa.vattrib;

/*-
 * #%L
 * hapi-fhir-storage-batch2-jobs
 * %%
 * Copyright (C) 2014 - 2022 Smile CDR, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import ca.uhn.fhir.batch2.api.IJobDataSink;
import ca.uhn.fhir.batch2.api.ILastJobStepWorker;
import ca.uhn.fhir.batch2.api.JobExecutionFailedException;
import ca.uhn.fhir.batch2.api.RunOutcome;
import ca.uhn.fhir.batch2.api.StepExecutionDetails;
import ca.uhn.fhir.batch2.api.VoidModel;
import ca.uhn.fhir.batch2.jobs.imprt.BulkImportJobParameters;
import ca.uhn.fhir.batch2.jobs.imprt.ConsumeFilesStep;
import ca.uhn.fhir.batch2.jobs.imprt.NdJsonFileJson;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.i18n.Msg;
import ca.uhn.fhir.interceptor.model.RequestPartitionId;
import ca.uhn.fhir.jpa.api.dao.DaoRegistry;
import ca.uhn.fhir.jpa.api.dao.IFhirResourceDao;
import ca.uhn.fhir.jpa.api.dao.IFhirSystemDao;
import ca.uhn.fhir.jpa.api.svc.IIdHelperService;
import ca.uhn.fhir.jpa.dao.tx.HapiTransactionService;
import ca.uhn.fhir.jpa.partition.SystemRequestDetails;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.api.server.storage.ResourcePersistentId;
import ca.uhn.fhir.rest.api.server.storage.TransactionDetails;
import ca.uhn.fhir.rest.server.exceptions.InvalidRequestException;
import ca.uhn.fhir.rest.server.exceptions.PreconditionFailedException;
import org.apache.commons.io.LineIterator;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class CustomConsumeFilesStep implements ILastJobStepWorker<BulkImportJobParameters, NdJsonFileJson> {

	private static final Logger ourLog = LoggerFactory.getLogger(ConsumeFilesStep.class);
	@Autowired
	private FhirContext myCtx;
	@Autowired
	private DaoRegistry myDaoRegistry;
	@Autowired
	private HapiTransactionService myHapiTransactionService;
	@Autowired
	private IIdHelperService myIdHelperService;
	@Autowired
	private IFhirSystemDao<?, ?> mySystemDao;

	@Nonnull
	@Override
	public RunOutcome run(@Nonnull StepExecutionDetails<BulkImportJobParameters, NdJsonFileJson> theStepExecutionDetails, @Nonnull IJobDataSink<VoidModel> theDataSink) {
		
		
		String ndjson = theStepExecutionDetails.getData().getNdJsonText();
		String sourceName = theStepExecutionDetails.getData().getSourceName();
		
		myCtx = FhirContext.forR4();
		IParser jsonParser = myCtx.newJsonParser();
		
		List<IBaseResource> resources = new ArrayList<>();
		if (sourceName.equals("ndjson")) {
		LineIterator lineIter = new LineIterator(new StringReader(ndjson));
		while (lineIter.hasNext()) {
			String next = lineIter.next();
			if (isNotBlank(next)) {
				IBaseResource parsed;
				try {
					parsed = jsonParser.parseResource(next);
				} catch (DataFormatException e) {
					throw new JobExecutionFailedException(Msg.code(2052) + "Failed to parse resource: " + e, e);
				}
				resources.add(parsed);
			}
		}
		} else {
			IBaseResource parsed;
			try {
				parsed = jsonParser.parseResource(ndjson);
			} catch (DataFormatException e) {
				throw new JobExecutionFailedException(Msg.code(2052) + "Failed to parse resource: " + e, e);
			}
			if (parsed.fhirType().equals("Bundle")) {
				Bundle collection = (Bundle)parsed;
				for (Iterator iterator = collection.getEntry().iterator(); iterator.hasNext();) {
					BundleEntryComponent entry = (BundleEntryComponent)iterator.next();
					resources.add(entry.getResource());
					
				}
				collection.getEntry().get(0).getResource();
			}
			System.out.println("The added type is " );
			//resources.add(parsed);
		}

		ourLog.info("Bulk loading {} resources from source {}", resources.size(), sourceName);

		storeResources(resources);

		return new RunOutcome(resources.size());
	}

	public void storeResources(List<IBaseResource> resources) {
		RequestDetails requestDetails = new SystemRequestDetails();
		int count = 0;
		for (IBaseResource next : resources) {
			updateResource(requestDetails, next);
			count++;
			System.out.println("The count is " + count);
			
		}
		
		
	}


	private <T extends IBaseResource> void updateResource(RequestDetails theRequestDetails, T theResource) {
		IFhirResourceDao<T> dao = myDaoRegistry.getResourceDao(theResource);
		try {
			System.out.println("=========================Updating the resource of type " + theResource.fhirType());
			dao.update(theResource, null, true, theRequestDetails);
		} catch (InvalidRequestException | PreconditionFailedException e) {
			String msg = "Failure during bulk import: " + e;
			ourLog.error(msg);
			
			throw new JobExecutionFailedException(Msg.code(2053) + msg, e);
		}
	}
}
