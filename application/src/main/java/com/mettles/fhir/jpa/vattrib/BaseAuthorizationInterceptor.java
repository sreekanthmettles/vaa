package com.mettles.fhir.jpa.vattrib;

import java.util.List;

import ca.uhn.fhir.i18n.Msg;
import ca.uhn.fhir.rest.api.RequestTypeEnum;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;
import ca.uhn.fhir.rest.server.interceptor.auth.AuthorizationInterceptor;
import ca.uhn.fhir.rest.server.interceptor.auth.IAuthRule;
import ca.uhn.fhir.rest.server.interceptor.auth.RuleBuilder;

public class BaseAuthorizationInterceptor extends AuthorizationInterceptor {

	@Override
	public List<IAuthRule> buildRuleList(RequestDetails theRequestDetails) {
		// Process this header

		String authHeader = theRequestDetails.getHeader("Authorization");
		String userGroup = theRequestDetails.getHeader("Group");
		String path = theRequestDetails.getRequestPath();
		RequestTypeEnum requestType = theRequestDetails.getRequestType();

		RuleBuilder builder = new RuleBuilder();

		// Allow metadata request
		if (path.equals("metadata")) {
			return builder.allowAll().build();
		} else {
			if (userGroup == null) {
				throw new AuthenticationException(Msg.code(644) + "Missing or invalid Authorization header value");
			} else {
				if (userGroup.equals("vaGroup")) {
					if (requestType.equals(RequestTypeEnum.POST)) {
						System.out.println("accept vagroup with post");
						return new RuleBuilder().allowAll().build();
					} else {
						throw new AuthenticationException(Msg.code(654) + "Only Post operations are allowed");
					}

				} else if (userGroup.equals("cmsGroup")) {
					if (requestType.equals(RequestTypeEnum.GET)) {
						System.out.println("accept cmsgroup with read");
						return new RuleBuilder().allowAll().build();
					} else {
						throw new AuthenticationException(Msg.code(654) + "Only Get operations are allowed");
					}

				} else if (userGroup.equals("admin")) {
					System.out.println("accept cmsgroup with read");
					return new RuleBuilder().allowAll().build();
				} else {
					System.out.println("deny========");
					return new RuleBuilder().denyAll().build();
				}
			}
		}

	}

}