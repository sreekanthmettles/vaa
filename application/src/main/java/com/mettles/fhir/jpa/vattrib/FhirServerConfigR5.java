package com.mettles.fhir.jpa.vattrib;

import ca.uhn.fhir.jpa.config.r5.JpaR5Config;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.mettles.fhir.jpa.vattrib.annotations.OnR5Condition;

@Configuration
@Conditional(OnR5Condition.class)
@Import({
	StarterJpaConfig.class,
	JpaR5Config.class,
	ElasticsearchConfig.class
})
public class FhirServerConfigR5 {
}
