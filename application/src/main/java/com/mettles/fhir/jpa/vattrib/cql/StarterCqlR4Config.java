package com.mettles.fhir.jpa.vattrib.cql;

import ca.uhn.fhir.cql.config.CqlR4Config;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Import;

import com.mettles.fhir.jpa.vattrib.annotations.OnR4Condition;

@Conditional({OnR4Condition.class, CqlConfigCondition.class})
@Import({CqlR4Config.class})
public class StarterCqlR4Config {
}
