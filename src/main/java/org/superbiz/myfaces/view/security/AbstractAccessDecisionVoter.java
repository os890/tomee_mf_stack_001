/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.superbiz.myfaces.view.security;

import org.apache.deltaspike.security.api.authorization.AccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractAccessDecisionVoter implements AccessDecisionVoter
{
    protected SecurityViolation newSecurityViolation(String reason)
    {
        return new SimpleSecurityViolation(reason);
    }

    public Set<SecurityViolation> checkPermission(AccessDecisionVoterContext accessDecisionVoterContext)
    {
        Set<SecurityViolation> result = new HashSet<SecurityViolation>();

        checkPermission(accessDecisionVoterContext, result);

        return result;
    }

    protected abstract void checkPermission(AccessDecisionVoterContext accessDecisionVoterContext,
                                            Set<SecurityViolation> violations);


    private class SimpleSecurityViolation implements SecurityViolation
    {
        private static final long serialVersionUID = -5017812464381395966L;

        private final String reason;

        SimpleSecurityViolation(String reason)
        {
            this.reason = reason;
        }

        @Override
        public String getReason()
        {
            return reason;
        }
    }
}
