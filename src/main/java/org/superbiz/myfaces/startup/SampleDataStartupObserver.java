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
package org.superbiz.myfaces.startup;

import org.apache.deltaspike.core.api.exclude.Exclude;
import org.os890.cdi.ext.common.startup.event.StartupEvent;
import org.superbiz.myfaces.domain.User;
import org.superbiz.myfaces.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import static org.apache.deltaspike.core.api.projectstage.ProjectStage.Development;
import static org.apache.deltaspike.core.api.projectstage.ProjectStage.IntegrationTest;

//e.g. via the std. JSF project-stage or CODI project-stage org.apache.myfaces.extensions.cdi.ProjectStage=Development
@Exclude(exceptIfProjectStage = {Development.class, IntegrationTest.class})
@ApplicationScoped
public class SampleDataStartupObserver
{
    @Inject
    private EntityManager entityManager;

    protected void createSampleData(@Observes StartupEvent startupEvent, UserRepository userRepository)
    {
        initDB(); //ok since we are in project-stage dev. or integration-test

        User user = userRepository.createNewEntity();
        user.setUserName("demo");
        user.setFirstName("Demo");
        user.setLastName("User");
        user.setPassword("demo");
        userRepository.save(user);
    }

    private void initDB()
    {
        entityManager.getTransaction().begin();
        Query query = entityManager.createNativeQuery("TRUNCATE SCHEMA public AND COMMIT");
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }
}
