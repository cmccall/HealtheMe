/**
 * Copyright (C) 2012 KRM Associates, Inc. healtheme@krminc.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.krminc.phr.api.service.clinical;

import com.krminc.phr.api.converter.clinical.CcrMedicationConverter;
import com.krminc.phr.api.service.Api;
import com.krminc.phr.dao.PersistenceService;
import com.krminc.phr.domain.clinical.CcrMedication;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import com.sun.jersey.api.core.ResourceContext;
import javax.ws.rs.WebApplicationException;
import javax.persistence.NoResultException;
import javax.persistence.EntityManager;

/**
 *
 * @author cmccall
 */

public class CcrMedicationResource {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
    protected Long id;
  
    /** Creates a new instance of CcrMedicationResource */
    public CcrMedicationResource() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get method for retrieving an instance of CcrMedication identified by id in XML format.
     *
     * @param id identifier for the entity
     * @return an instance of CcrMedicationConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public CcrMedicationConverter get() {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            return new CcrMedicationConverter(getEntity(), uriInfo.getAbsolutePath(), Api.DEFAULT_EXPAND_LEVEL);
        } finally {
            PersistenceService.getInstance().close();
        }
    }

    /**
     * Returns an instance of CcrMedication identified by id.
     *
     * @param id identifier for the entity
     * @return an instance of CcrMedication
     */
    protected CcrMedication getEntity() {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        try {
            return (CcrMedication) em.createQuery("SELECT e FROM CcrMedication e where e.id = :id").setParameter("id", id).getSingleResult();
        } catch (NoResultException ex) {
            throw new WebApplicationException(new Throwable("Resource for " + uriInfo.getAbsolutePath() + " does not exist."), 404);
        }
    }
}