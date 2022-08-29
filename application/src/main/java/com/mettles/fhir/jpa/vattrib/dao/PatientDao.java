package com.mettles.fhir.jpa.vattrib.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;

import org.hl7.fhir.instance.model.api.IBaseMetaType;
import org.hl7.fhir.instance.model.api.IBaseParameters;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.RuntimeResourceDefinition;
import ca.uhn.fhir.jpa.api.config.DaoConfig;
import ca.uhn.fhir.jpa.api.dao.IFhirResourceDao;
import ca.uhn.fhir.jpa.api.model.DaoMethodOutcome;
import ca.uhn.fhir.jpa.api.model.DeleteConflictList;
import ca.uhn.fhir.jpa.api.model.DeleteMethodOutcome;
import ca.uhn.fhir.jpa.api.model.ExpungeOptions;
import ca.uhn.fhir.jpa.api.model.ExpungeOutcome;
import ca.uhn.fhir.jpa.dao.r4.FhirResourceDaoPatientR4;
import ca.uhn.fhir.jpa.model.entity.BaseHasResource;
import ca.uhn.fhir.jpa.model.entity.IBaseResourceEntity;
import ca.uhn.fhir.jpa.model.entity.ResourceTable;
import ca.uhn.fhir.jpa.model.entity.ResourceTag;
import ca.uhn.fhir.jpa.model.entity.TagTypeEnum;
import ca.uhn.fhir.jpa.partition.IRequestPartitionHelperSvc;
import ca.uhn.fhir.jpa.searchparam.SearchParameterMap;
import ca.uhn.fhir.rest.api.EncodingEnum;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.PatchTypeEnum;
import ca.uhn.fhir.rest.api.ValidationModeEnum;
import ca.uhn.fhir.rest.api.server.IBundleProvider;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.api.server.storage.ResourcePersistentId;
import ca.uhn.fhir.rest.api.server.storage.TransactionDetails;
import ca.uhn.fhir.rest.param.TokenOrListParam;
import ca.uhn.fhir.rest.param.TokenParam;
public class PatientDao implements IFhirResourceDao<Patient>{

	
	
	FhirResourceDaoPatientR4 originalDao;
    public PatientDao(){
       
        super();
        System.out.println("register patient dao");
        
    }
    
   

    public PatientDao(IFhirResourceDao resourceDao) {
        super();
        this.originalDao = (FhirResourceDaoPatientR4)resourceDao;
        
    }
   
    
   

    @Override
	public DaoMethodOutcome update(Patient theResource, String theMatchUrl, boolean thePerformIndexing, boolean theForceUpdateVersion, RequestDetails theRequest, @Nonnull TransactionDetails theTransactionDetails) {
        System.out.println("dao updating of patient with original 6");
        MatchedResource res = getMemberMatch(theResource);
        if (!res.isMatched()) {
        	System.out.println("Not Matched so creating ");
        	return originalDao.create(theResource);
        } else {
        	System.out.println("Matched and updating the patient" + res.getPatient().getId());
            return originalDao.update(res.getPatient(), theMatchUrl, thePerformIndexing, theRequest);

        }
        
        
    }
    
    @Override
	public DaoMethodOutcome create(Patient theResource, String theIfNoneExist, boolean thePerformIndexing, @Nonnull TransactionDetails theTransactionDetails, RequestDetails theRequestDetails) {
        System.out.println("dao only create");
        return originalDao.create(theResource, theIfNoneExist, thePerformIndexing, theTransactionDetails, theRequestDetails);
    }



	@Override
	public FhirContext getContext() {
		// TODO Auto-generated method stub
		return originalDao.getContext();
	}



	@Override
	public IBaseResource toResource(BaseHasResource theEntity, boolean theForHistoryOperation) {
		// TODO Auto-generated method stub
		return originalDao.toResource(theEntity, theForHistoryOperation);
	}



	@Override
	public <R extends IBaseResource> R toResource(Class<R> theResourceType, IBaseResourceEntity theEntity,
			Collection<ResourceTag> theTagList, boolean theForHistoryOperation) {
		// TODO Auto-generated method stub
		return originalDao.toResource(theResourceType, theEntity, theTagList, theForHistoryOperation);
	}



	@Override
	public DaoMethodOutcome create(Patient theResource) {
		
		return originalDao.create(getMemberMatch(theResource).getPatient());
	}



	@Override
	public DaoMethodOutcome create(Patient theResource, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		System.out.println("dao only create 2");
		return originalDao.create(getMemberMatch(theResource).getPatient(), theRequestDetails);
	}



	@Override
	public DaoMethodOutcome create(Patient theResource, String theIfNoneExist) {
		// TODO Auto-generated method stub
		System.out.println("dao only create 3");
		return originalDao.create(getMemberMatch(theResource).getPatient(), theIfNoneExist);
	}



	@Override
	public DaoMethodOutcome create(Patient theResource, String theIfNoneExist, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		System.out.println("dao only create 4");
		return originalDao.create(getMemberMatch(theResource).getPatient(), theIfNoneExist, theRequestDetails);
	}



	@Override
	public DaoMethodOutcome delete(IIdType theResource) {
		// TODO Auto-generated method stub
		return originalDao.delete(theResource);
	}



	@Override
	public DaoMethodOutcome delete(IIdType theResource, DeleteConflictList theDeleteConflictsListToPopulate,
			RequestDetails theRequestDetails, TransactionDetails theTransactionDetails) {
		// TODO Auto-generated method stub
		return originalDao.delete(theResource, theDeleteConflictsListToPopulate, theRequestDetails, theTransactionDetails);
	}



	@Override
	public DaoMethodOutcome delete(IIdType theResource, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return originalDao.delete(theResource, theRequestDetails);
	}



	@Override
	public DeleteMethodOutcome deleteByUrl(String theUrl, DeleteConflictList theDeleteConflictsListToPopulate,
			RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return originalDao.deleteByUrl(theUrl, theDeleteConflictsListToPopulate, theRequestDetails);
	}



	@Override
	public DeleteMethodOutcome deleteByUrl(String theString, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return originalDao.deleteByUrl(theString, theRequestDetails);
	}



	@Override
	public ExpungeOutcome expunge(ExpungeOptions theExpungeOptions, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return originalDao.expunge(theExpungeOptions, theRequestDetails);
	}



	@Override
	public ExpungeOutcome expunge(IIdType theIIdType, ExpungeOptions theExpungeOptions, RequestDetails theRequest) {
		// TODO Auto-generated method stub
		return originalDao.expunge(theIIdType, theExpungeOptions, theRequest);
	}



	@Override
	public ExpungeOutcome forceExpungeInExistingTransaction(IIdType theId, ExpungeOptions theExpungeOptions,
			RequestDetails theRequest) {
		// TODO Auto-generated method stub
		return originalDao.forceExpungeInExistingTransaction(theId, theExpungeOptions, theRequest);
	}



	@Override
	public Class<Patient> getResourceType() {
		// TODO Auto-generated method stub
		return originalDao.getResourceType();
	}



	@Override
	public IBundleProvider history(Date theSince, Date theUntil, Integer theOffset, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return originalDao.history(theSince, theUntil, theOffset, theRequestDetails);
	}



	@Override
	public IBundleProvider history(IIdType theId, Date theSince, Date theUntil, Integer theOffset,
			RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return originalDao.history(theId, theSince, theUntil, theOffset, theRequestDetails);
	}



	@Override
	public <MT extends IBaseMetaType> MT metaAddOperation(IIdType theId1, MT theMetaAdd,
			RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return originalDao.metaAddOperation(theId1, theMetaAdd, theRequestDetails);
	}



	@Override
	public <MT extends IBaseMetaType> MT metaDeleteOperation(IIdType theId1, MT theMetaDel,
			RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return originalDao.metaDeleteOperation(theId1, theMetaDel, theRequestDetails);
	}



	@Override
	public <MT extends IBaseMetaType> MT metaGetOperation(Class<MT> theType, IIdType theId,
			RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return originalDao.metaGetOperation(theType,theId,theRequestDetails);

	}



	@Override
	public <MT extends IBaseMetaType> MT metaGetOperation(Class<MT> theType, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return originalDao.metaGetOperation(theType, theRequestDetails);

	}



	@Override
	public DaoMethodOutcome patch(IIdType theId, String theConditionalUrl, PatchTypeEnum thePatchType,
			String thePatchBody, IBaseParameters theFhirPatchBody, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return originalDao.patch(theId, theConditionalUrl, thePatchType, thePatchBody, theFhirPatchBody, theRequestDetails);
	}



	@Override
	public Patient read(IIdType theId) {
		// TODO Auto-generated method stub
		return originalDao.read(theId);
	}



	@Override
	public Patient readByPid(ResourcePersistentId thePid) {
		// TODO Auto-generated method stub
		return originalDao.readByPid(thePid);
	}



	@Override
	public Patient read(IIdType theId, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return originalDao.read(theId, theRequestDetails);
	}



	@Override
	public Patient read(IIdType theId, RequestDetails theRequestDetails, boolean theDeletedOk) {
		// TODO Auto-generated method stub
		return originalDao.read(theId, theRequestDetails, theDeletedOk);
	}



	@Override
	public BaseHasResource readEntity(IIdType theId, RequestDetails theRequest) {
		// TODO Auto-generated method stub
		return originalDao.readEntity(theId, theRequest);
	}



	@Override
	public BaseHasResource readEntity(IIdType theId, boolean theCheckForForcedId, RequestDetails theRequest) {
		// TODO Auto-generated method stub
		return originalDao.readEntity(theId, theCheckForForcedId, theRequest);
	}



	@Override
	public void reindex(Patient theResource, ResourceTable theEntity) {
		// TODO Auto-generated method stub
		 originalDao.reindex(theResource, theEntity);
		
	}



	@Override
	public void removeTag(IIdType theId, TagTypeEnum theTagType, String theSystem, String theCode,
			RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		originalDao.removeTag(theId, theTagType, theSystem, theCode, theRequestDetails);
		
	}



	@Override
	public void removeTag(IIdType theId, TagTypeEnum theTagType, String theSystem, String theCode) {
		// TODO Auto-generated method stub
		originalDao.removeTag(theId, theTagType, theSystem, theCode);
		
	}



	@Override
	public IBundleProvider search(SearchParameterMap theParams) {
		// TODO Auto-generated method stub
		return originalDao.search(theParams);
	}



	@Override
	public IBundleProvider search(SearchParameterMap theParams, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return originalDao.search(theParams, theRequestDetails);
	}



	@Override
	public IBundleProvider search(SearchParameterMap theParams, RequestDetails theRequestDetails,
			HttpServletResponse theServletResponse) {
		// TODO Auto-generated method stub
		return originalDao.search(theParams, theRequestDetails, theServletResponse);
	}



	@Override
	public void translateRawParameters(Map<String, List<String>> theSource, SearchParameterMap theTarget) {
		// TODO Auto-generated method stub
		originalDao.translateRawParameters(theSource, theTarget);
		
	}



	@Override
	public DaoMethodOutcome update(Patient theResource) {
		// TODO Auto-generated method stub
		System.out.println("In update 1");
		return originalDao.update(getMemberMatch(theResource).getPatient());
	}



	@Override
	public DaoMethodOutcome update(Patient theResource, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		System.out.println("In update 2");
		MatchedResource res = getMemberMatch(theResource);
        if (!res.isMatched()) {
        	System.out.println("Not Matched so creating ");
        	return originalDao.create(theResource);
        } else {
        	System.out.println("Matched and updating the patient" + res.getPatient().getId());
            return originalDao.update(res.getPatient(), theRequestDetails);

        }
	}


	@Override
	public DaoMethodOutcome update(Patient theResource, String theMatchUrl) {
		// TODO Auto-generated method stub
		System.out.println("In update 3");
		MatchedResource res = getMemberMatch(theResource);
        if (!res.isMatched()) {
        	System.out.println("Not Matched so creating ");
        	return originalDao.create(theResource);
        } else {
        	System.out.println("Matched and updating the patient" + res.getPatient().getId());
            return originalDao.update(res.getPatient(), theMatchUrl);

        }
	}



	@Override
	public DaoMethodOutcome update(Patient theResource, String theMatchUrl, boolean thePerformIndexing,
			RequestDetails theRequestDetails) {
		System.out.println("In update 4");
		// TODO Auto-generated method stub
		MatchedResource res = getMemberMatch(theResource);
        if (!res.isMatched()) {
        	System.out.println("Not Matched so creating ");
        	return originalDao.create(theResource);
        } else {
        	System.out.println("Matched and updating the patient" + res.getPatient().getId());
            return originalDao.update(res.getPatient(), theMatchUrl, thePerformIndexing, theRequestDetails);

        }
	}



	@Override
	public DaoMethodOutcome update(Patient theResource, String theMatchUrl, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		System.out.println("In update 5");
		MatchedResource res = getMemberMatch(theResource);
        if (!res.isMatched()) {
        	System.out.println("Not Matched so creating ");
        	return originalDao.create(theResource);
        } else {
        	System.out.println("Matched and updating the patient" + res.getPatient().getId());
            return originalDao.update(res.getPatient(), theMatchUrl,  theRequestDetails);

        }
	}



	@Override
	public MethodOutcome validate(Patient theResource, IIdType theId, String theRawResource, EncodingEnum theEncoding,
			ValidationModeEnum theMode, String theProfile, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return originalDao.validate(theResource, theId, theRawResource, theEncoding, theMode, theProfile, theRequestDetails);
	}



	@Override
	public RuntimeResourceDefinition validateCriteriaAndReturnResourceDefinition(String criteria) {
		// TODO Auto-generated method stub
		return originalDao.validateCriteriaAndReturnResourceDefinition(criteria);
	}



	@Override
	public DeleteMethodOutcome deletePidList(String theUrl, Collection<ResourcePersistentId> theResourceIds,
			DeleteConflictList theDeleteConflicts, RequestDetails theRequest) {
		// TODO Auto-generated method stub
		return originalDao.deletePidList(theUrl, theResourceIds, theDeleteConflicts, theRequest);
	}



	@Override
	public void reindex(ResourcePersistentId theResourcePersistentId, RequestDetails theRequest,
			TransactionDetails theTransactionDetails) {
		// TODO Auto-generated method stub
		originalDao.reindex(theResourcePersistentId, theRequest, theTransactionDetails);;
		
	}
	
	private MatchedResource getMemberMatch(Patient theResource) {
		MatchedResource matchedResource = new MatchedResource(theResource);
        String VA_SYSTEM = "http://va.gov/mpi";
        String MEDICARE_SYSTEM = "http://hl7.org/fhir/sid/us-mbi";
        SearchParameterMap map = new SearchParameterMap();
        Identifier vaIdentifier = null;
        Identifier medicareIdentifier = null;
        
        for (Identifier identififer :theResource.getIdentifier()){
            if (identififer.getSystem().equals(VA_SYSTEM)){
                vaIdentifier = identififer;
                
            }
            else if (identififer.getSystem().equals(MEDICARE_SYSTEM)){
            	medicareIdentifier = identififer;
                //map.add(Patient.IDENTIFIER, (new TokenParam().setValue(identififer.getValue())));
            }
        }
        TokenOrListParam tokenOrListParam = new TokenOrListParam();
    	
        boolean foundIdentifier = false;
        if ((medicareIdentifier != null) && (medicareIdentifier.getValue() != null) 
        		&& (!medicareIdentifier.getValue().equals("UNKNOWN"))) {
        	
        	TokenParam tagParam = new TokenParam();
        	tagParam.setSystem(MEDICARE_SYSTEM);
        	tagParam.setValue(medicareIdentifier.getValue());
        	tokenOrListParam.addOr(tagParam);
        	foundIdentifier = true;
        	
        	//map.add(Patient.IDENTIFIER, (new TokenParam().setValue(medicareIdentifier.getValue())));
        } 
        if ((vaIdentifier != null) && (vaIdentifier.getValue() != null) 
        		&& (!vaIdentifier.getValue().equals("UNKNOWN"))) {
        	TokenParam tagParam = new TokenParam();
        	tagParam.setSystem(VA_SYSTEM);
        	tagParam.setValue(vaIdentifier.getValue());
        	tokenOrListParam.addOr(tagParam);
        	foundIdentifier = true;
        } 
        map.add(Patient.IDENTIFIER.getParamName(), tokenOrListParam);
        IBundleProvider result = originalDao.search(map);
        if ((result != null) && (result.size()> 0)){
        	List<IBaseResource> allResources = result.getAllResources();
        	Patient matchedPatient = (Patient)allResources.get(0); 
        	System.out.println("=======================================Found a matching patient");
        	
        	boolean foundVaIdentifierInResult = false;
        	boolean foundMedicareIdentifierInResult = false;
        	for (Identifier identififer :matchedPatient.getIdentifier()){
                if (identififer.getSystem().equals(VA_SYSTEM)){
                	foundVaIdentifierInResult = true;
                    
                }
                if (identififer.getSystem().equals(MEDICARE_SYSTEM)){
                	foundMedicareIdentifierInResult = true;
                    
                }
            }
        	if(!foundVaIdentifierInResult && (vaIdentifier != null)) {
        		matchedPatient.getIdentifier().add(vaIdentifier);
        	}
        	if(!foundMedicareIdentifierInResult && (medicareIdentifier != null)) {
        		matchedPatient.getIdentifier().add(medicareIdentifier);
        	}
        	matchedResource.setMatched();
            matchedResource.setPatient(matchedPatient);
           
        }
        map.clean();
        
        return matchedResource;
    }
	
	private class MatchedResource {
		Patient patient;
		boolean matched;
		public MatchedResource(Patient patient) {
			this.patient = patient;
			matched = false;
			// TODO Auto-generated constructor stub
		}
		public void setMatched() {
			matched = true;
		}
		public boolean isMatched() {
			return matched;
		}
		public Patient getPatient() {
			return patient;
		}
		public void setPatient(Patient patient) {
			this.patient = patient;
		}
	}
	
}

