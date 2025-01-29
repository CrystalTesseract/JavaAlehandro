package org.alexander.project;

import org.alexander.project.config.IntegrationTestBase;
import org.alexander.project.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonControllerNegativeIntegrationTest extends IntegrationTestBase {
    @Autowired
    private PersonService db;


}
