package com.mettles.fhir.jpa.vattrib.dao;

import java.util.List;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Coverage;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Reference;

import ca.uhn.fhir.jpa.api.dao.IFhirResourceDao;
import ca.uhn.fhir.jpa.api.model.DaoMethodOutcome;
import ca.uhn.fhir.jpa.dao.BaseHapiFhirResourceDao;
import ca.uhn.fhir.jpa.dao.r4.FhirResourceDaoPatientR4;
import ca.uhn.fhir.jpa.rp.r4.CoverageResourceProvider;
import ca.uhn.fhir.jpa.searchparam.SearchParameterMap;
import ca.uhn.fhir.rest.annotation.ConditionalUrlParam;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.api.server.IBundleProvider;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.api.server.storage.TransactionDetails;
import ca.uhn.fhir.rest.param.TokenOrListParam;
import ca.uhn.fhir.rest.param.TokenParam;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.hl7.fhir.instance.model.api.IBaseMetaType;
import org.hl7.fhir.instance.model.api.IBaseParameters;
import org.hl7.fhir.r4.model.Identifier;
import org.springframework.beans.factory.annotation.Autowired;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.RuntimeResourceDefinition;
import ca.uhn.fhir.jpa.api.config.DaoConfig;
import ca.uhn.fhir.jpa.api.model.DeleteConflictList;
import ca.uhn.fhir.jpa.api.model.DeleteMethodOutcome;
import ca.uhn.fhir.jpa.api.model.ExpungeOptions;
import ca.uhn.fhir.jpa.api.model.ExpungeOutcome;
import ca.uhn.fhir.jpa.model.entity.BaseHasResource;
import ca.uhn.fhir.jpa.model.entity.IBaseResourceEntity;
import ca.uhn.fhir.jpa.model.entity.ResourceTable;
import ca.uhn.fhir.jpa.model.entity.ResourceTag;
import ca.uhn.fhir.jpa.model.entity.TagTypeEnum;
import ca.uhn.fhir.jpa.partition.IRequestPartitionHelperSvc;
import ca.uhn.fhir.rest.api.EncodingEnum;
import ca.uhn.fhir.rest.api.PatchTypeEnum;
import ca.uhn.fhir.rest.api.ValidationModeEnum;
import ca.uhn.fhir.rest.api.server.storage.ResourcePersistentId;

public class CoverageDao implements IFhirResourceDao<Coverage>{

    
    IFhirResourceDao originalDao;
    IFhirResourceDao patientDao;
    
    public CoverageDao(){
       
        super();
        System.out.println("register coverage dao");
        
        
    }


    public CoverageDao(IFhirResourceDao resourceDao,IFhirResourceDao patientDao) {
        super();
        this.originalDao = resourceDao;
        this.patientDao = patientDao;
        
    }


    @Override
	public DaoMethodOutcome update(Coverage theResource, String theMatchUrl, boolean thePerformIndexing, boolean theForceUpdateVersion, RequestDetails theRequest, @Nonnull TransactionDetails theTransactionDetails) {
        System.out.println("dao updating of coverage with original");
        MatchedResource mr= getMatchedResource(theResource);
        if (mr.isMatched())
            return originalDao.update(mr.getCoverage(), theMatchUrl, thePerformIndexing, theForceUpdateVersion, theRequest, theTransactionDetails);
        else
        	return new DaoMethodOutcome();
        
    }
    
    @Override
	public DaoMethodOutcome create(Coverage theResource, String theIfNoneExist, boolean thePerformIndexing, @Nonnull TransactionDetails theTransactionDetails, RequestDetails theRequestDetails) {
        System.out.println("dao creating of coverage with original");
        MatchedResource mr= getMatchedResource(theResource);
        if (mr.isMatched())
        return originalDao.create(mr.getCoverage(), theIfNoneExist, thePerformIndexing, theTransactionDetails, theRequestDetails);
        else
        	return new DaoMethodOutcome();
        
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
	public DaoMethodOutcome create(Coverage theResource) {
		MatchedResource mr= getMatchedResource(theResource);
        if (mr.isMatched())
		return originalDao.create(mr.getCoverage());
        else
        	return new DaoMethodOutcome();
	}



	@Override
	public DaoMethodOutcome create(Coverage theResource, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		System.out.println("dao only create 2");
		MatchedResource mr= getMatchedResource(theResource);
        if (mr.isMatched())
		return originalDao.create(mr.getCoverage(), theRequestDetails);
        else
        	return new DaoMethodOutcome();
	}



	@Override
	public DaoMethodOutcome create(Coverage theResource, String theIfNoneExist) {
		// TODO Auto-generated method stub
		System.out.println("dao only create 3");
		MatchedResource mr= getMatchedResource(theResource);
        if (mr.isMatched())
		return originalDao.create(mr.getCoverage(), theIfNoneExist);
        else
        	return new DaoMethodOutcome();
	}



	@Override
	public DaoMethodOutcome create(Coverage theResource, String theIfNoneExist, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		System.out.println("dao only create 4");
		MatchedResource mr= getMatchedResource(theResource);
        if (mr.isMatched())
		return originalDao.create(mr.getCoverage(), theIfNoneExist, theRequestDetails);
        else
        	return new DaoMethodOutcome();
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
	public Class<Coverage> getResourceType() {
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
		return (MT) originalDao.metaAddOperation(theId1, theMetaAdd, theRequestDetails);
	}



	@Override
	public <MT extends IBaseMetaType> MT metaDeleteOperation(IIdType theId1, MT theMetaDel,
			RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return (MT) originalDao.metaDeleteOperation(theId1, theMetaDel, theRequestDetails);
	}



	@Override
	public <MT extends IBaseMetaType> MT metaGetOperation(Class<MT> theType, IIdType theId,
			RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return (MT) originalDao.metaGetOperation(theType,theId,theRequestDetails);

	}



	@Override
	public <MT extends IBaseMetaType> MT metaGetOperation(Class<MT> theType, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return (MT) originalDao.metaGetOperation(theType, theRequestDetails);

	}



	@Override
	public DaoMethodOutcome patch(IIdType theId, String theConditionalUrl, PatchTypeEnum thePatchType,
			String thePatchBody, IBaseParameters theFhirPatchBody, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return originalDao.patch(theId, theConditionalUrl, thePatchType, thePatchBody, theFhirPatchBody, theRequestDetails);
	}



	@Override
	public Coverage read(IIdType theId) {
		// TODO Auto-generated method stub
		return (Coverage) originalDao.read(theId);
	}



	@Override
	public Coverage readByPid(ResourcePersistentId thePid) {
		// TODO Auto-generated method stub
		return (Coverage) originalDao.readByPid(thePid);
	}



	@Override
	public Coverage read(IIdType theId, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return (Coverage) originalDao.read(theId, theRequestDetails);
	}



	@Override
	public Coverage read(IIdType theId, RequestDetails theRequestDetails, boolean theDeletedOk) {
		// TODO Auto-generated method stub
		return (Coverage) originalDao.read(theId, theRequestDetails, theDeletedOk);
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
	public void reindex(Coverage theResource, ResourceTable theEntity) {
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
    private MatchedResource getMatchedResource(Coverage coverage) {
    	MatchedResource mr = new MatchedResource(coverage);
    	String subscriberId = coverage.getSubscriberId();
    	String VA_SYSTEM = "http://va.gov/mpi";
        String MEDICARE_SYSTEM = "http://hl7.org/fhir/sid/us-mbi";
        SearchParameterMap map = new SearchParameterMap();
        TokenOrListParam tokenOrListParam = new TokenOrListParam();
        TokenParam tagParam = new TokenParam();
    	tagParam.setSystem(MEDICARE_SYSTEM);
    	tagParam.setValue(subscriberId);
    	tokenOrListParam.addOr(tagParam);
    	tagParam = new TokenParam();
    	tagParam.setSystem(VA_SYSTEM);
    	tagParam.setValue(subscriberId);
    	tokenOrListParam.addOr(tagParam);
    	map.add(Patient.IDENTIFIER.getParamName(), tokenOrListParam);
        IBundleProvider result = patientDao.search(map);
        if ((result != null) && (result.size()> 0)){
        	List<IBaseResource> allResources = result.getAllResources();
        	Patient matchedPatient = (Patient)allResources.get(0); 
        	coverage.setBeneficiary(new Reference(matchedPatient.getIdElement()));
        	mr.setCoverage(coverage);
        	mr.setMatched();
        }
    	return mr;
    }
    
    private class MatchedResource {
		Patient patient;
		Coverage coverage;
		boolean matched;
		public MatchedResource(Coverage coverage) {
			this.coverage = coverage;
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
		public Coverage getCoverage() {
			return coverage;
		}
		public void setCoverage(Coverage coverage) {
			this.coverage = coverage;
		}
	}

	@Override
	public DaoMethodOutcome update(Coverage theResource) {
		// TODO Auto-generated method stub
		MatchedResource mr= getMatchedResource(theResource);
        if (mr.isMatched())
            return originalDao.update(mr.getCoverage());
        else
        	return new DaoMethodOutcome();
	}


	@Override
	public DaoMethodOutcome update(Coverage theResource, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		MatchedResource mr= getMatchedResource(theResource);
        if (mr.isMatched())
            return originalDao.update(mr.getCoverage(), theRequestDetails);
        else
        	return new DaoMethodOutcome();
	}


	@Override
	public DaoMethodOutcome update(Coverage theResource, String theMatchUrl) {
		// TODO Auto-generated method stub
		MatchedResource mr= getMatchedResource(theResource);
        if (mr.isMatched())
            return originalDao.update(mr.getCoverage(), theMatchUrl);
        else
        	return new DaoMethodOutcome();
	}


	@Override
	public DaoMethodOutcome update(Coverage theResource, String theMatchUrl, boolean thePerformIndexing,
			RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		MatchedResource mr= getMatchedResource(theResource);
        if (mr.isMatched())
            return originalDao.update(mr.getCoverage(), theMatchUrl, thePerformIndexing, theRequestDetails );
        else
        	return new DaoMethodOutcome();
	}


	@Override
	public DaoMethodOutcome update(Coverage theResource, String theMatchUrl, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		MatchedResource mr= getMatchedResource(theResource);
        if (mr.isMatched())
            return originalDao.update(mr.getCoverage(), theMatchUrl, theRequestDetails);
        else
        	return new DaoMethodOutcome();
	}


	@Override
	public MethodOutcome validate(Coverage theResource, IIdType theId, String theRawResource, EncodingEnum theEncoding,
			ValidationModeEnum theMode, String theProfile, RequestDetails theRequestDetails) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public RuntimeResourceDefinition validateCriteriaAndReturnResourceDefinition(String criteria) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DeleteMethodOutcome deletePidList(String theUrl, Collection<ResourcePersistentId> theResourceIds,
			DeleteConflictList theDeleteConflicts, RequestDetails theRequest) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void reindex(ResourcePersistentId theResourcePersistentId, RequestDetails theRequest,
			TransactionDetails theTransactionDetails) {
		// TODO Auto-generated method stub
		
	}

	
}

