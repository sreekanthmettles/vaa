package com.mettles.fhir.jpa.vattrib.api;

import org.hl7.fhir.r4.model.Coverage;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import ca.uhn.fhir.jpa.api.config.DaoConfig.IdStrategyEnum;
import ca.uhn.fhir.jpa.api.dao.IFhirResourceDao;
import ca.uhn.fhir.jpa.rp.r4.CoverageResourceProvider;
import ca.uhn.fhir.jpa.rp.r4.PatientResourceProvider;
import ca.uhn.fhir.rest.server.IResourceProvider;

import javax.servlet.ServletException;
import com.mettles.fhir.jpa.vattrib.AppProperties;
import com.mettles.fhir.jpa.vattrib.BaseAuthorizationInterceptor;
import com.mettles.fhir.jpa.vattrib.dao.CoverageDao;
import com.mettles.fhir.jpa.vattrib.dao.PatientDao;

@Import(AppProperties.class)
public class JpaRestfulServer extends BaseJpaRestfulServer {

  @Autowired
  AppProperties appProperties;

  private static final long serialVersionUID = 1L;
  ApplicationContext appCtx;

  public JpaRestfulServer() {
    super();
  }

  @Override
  protected void initialize() throws ServletException {
    super.initialize();
    daoConfig.setResourceServerIdStrategy(IdStrategyEnum.UUID);
    //registerInterceptor(new BaseAuthorizationInterceptor());
    // Add your own customization here
    daoRegistry.getResourceDao("Patient");
    PatientDao pd = new PatientDao(daoRegistry.getResourceDao("Patient"));
    
    daoRegistry.register(pd);
    CoverageDao cd = new CoverageDao(daoRegistry.getResourceDao("Coverage"),daoRegistry.getResourceDao("Patient"));
    daoRegistry.register(cd);
    for (IResourceProvider rp:this.getResourceProviders()){
        System.out.println(rp.getResourceType());
        if (rp.getResourceType().equals(Patient.class)){
          PatientResourceProvider pr = (PatientResourceProvider)rp;
          System.out.println("the class is " + pr.getDao().getClass().getName());
          pr.setDao(pd);
          //registerProvider(pr);
        }
        else if (rp.getResourceType().equals(Coverage.class)){
          CoverageResourceProvider cr = (CoverageResourceProvider)rp;
          cr.setDao(cd);
         //registerProvider(cr);
        }

      }

  }

}
