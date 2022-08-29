package com.mettles.fhir.jpa.vattrib;

import ca.uhn.fhir.batch2.jobs.imprt.NdJsonFileJson;

public class MixedJson extends NdJsonFileJson{
	private String type = "ndjson";
	public MixedJson() {
		// TODO Auto-generated constructor stub
		
	}
	public void setType(String type) {
		this.type = type;
	}
}
