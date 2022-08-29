package com.mettles.fhir.jpa.vattrib.cql;

import ca.uhn.fhir.cql.config.CqlDstu3Config;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.mettles.fhir.jpa.vattrib.annotations.OnDSTU3Condition;

@Configuration
@Conditional({OnDSTU3Condition.class, CqlConfigCondition.class})
@Import({CqlDstu3Config.class})
public class StarterCqlDstu3Config {
}
