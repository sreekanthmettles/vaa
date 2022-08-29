package com.mettles.fhir.jpa.vattrib;

import ca.uhn.fhir.jpa.config.dstu3.JpaDstu3Config;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.mettles.fhir.jpa.vattrib.annotations.OnDSTU3Condition;
import com.mettles.fhir.jpa.vattrib.cql.StarterCqlDstu3Config;
import com.mettles.fhir.jpa.vattrib.mdm.MdmConfig;

@Configuration
@Conditional(OnDSTU3Condition.class)
@Import({
	StarterJpaConfig.class,
	JpaDstu3Config.class,
	StarterCqlDstu3Config.class,
	ElasticsearchConfig.class})
public class FhirServerConfigDstu3 {
}
