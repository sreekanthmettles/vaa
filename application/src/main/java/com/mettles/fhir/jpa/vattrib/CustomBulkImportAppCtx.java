package com.mettles.fhir.jpa.vattrib;

import ca.uhn.fhir.batch2.api.IJobStepWorker;
import ca.uhn.fhir.batch2.api.VoidModel;
import ca.uhn.fhir.batch2.model.JobDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ca.uhn.fhir.batch2.jobs.imprt.*;

@Configuration
public class CustomBulkImportAppCtx {

	public static final String JOB_BULK_IMPORT_PULL_MIXED ="BULK_IMPORT_PULL_MIXED";
	public static final int PARAM_MAXIMUM_BATCH_SIZE_DEFAULT = 800; // Avoid the 1000 SQL param limit

	@Bean
	public JobDefinition bulkImportMixedFileTypes() {
		return JobDefinition
			.newBuilder()
			.setJobDefinitionId(JOB_BULK_IMPORT_PULL_MIXED)
			.setJobDescription("FHIR Bulk Import using pull-based data source")
			.setJobDefinitionVersion(1)
			.setParametersType(BulkImportJobParameters.class)
			.addFirstStep(
				"fetch-files",
				"Fetch files for import",
				NdJsonFileJson.class,
				bulkImport2FetchFiles2())
			.addLastStep(
				"process-files",
				"Process files",
				bulkImport2ConsumeFiles2()
				)
			.build();
	}
	@Bean
	public IJobStepWorker<BulkImportJobParameters, VoidModel, NdJsonFileJson> bulkImport2FetchFiles2() {
		return new CustomFetchFiles();
	}

	@Bean
	public IJobStepWorker<BulkImportJobParameters, NdJsonFileJson, VoidModel> bulkImport2ConsumeFiles2() {
		return new CustomConsumeFilesStep();
	}
    
	
	
	
}
