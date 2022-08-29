package com.mettles.fhir.jpa.vattrib;

import ca.uhn.fhir.jpa.config.JpaDstu2Config;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.mettles.fhir.jpa.vattrib.annotations.OnDSTU2Condition;

@Configuration
@Conditional(OnDSTU2Condition.class)
@Import({
	StarterJpaConfig.class,
	JpaDstu2Config.class
})
public class FhirServerConfigDstu2 {
}
