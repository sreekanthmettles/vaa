package com.mettles.fhir.jpa.vattrib;

import ca.uhn.fhir.jpa.config.r4.JpaR4Config;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.mettles.fhir.jpa.vattrib.annotations.OnR4Condition;
import com.mettles.fhir.jpa.vattrib.cql.StarterCqlR4Config;
import com.mettles.fhir.jpa.vattrib.mdm.MdmConfig;

@Configuration
@Conditional(OnR4Condition.class)
@Import({
	StarterJpaConfig.class,
	JpaR4Config.class,
	StarterCqlR4Config.class,
	ElasticsearchConfig.class
})
public class FhirServerConfigR4 {
}
