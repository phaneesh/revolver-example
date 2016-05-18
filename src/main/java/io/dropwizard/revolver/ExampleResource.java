/*
 * Copyright (c) 2016 Phaneesh Nagaraja <phaneesh.n@gmail.com>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.dropwizard.revolver;

import com.codahale.metrics.annotation.Metered;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.ByteSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.zookeeper.common.IOUtils;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author phaneesh
 */
@Path("/")
@Slf4j
@Singleton
@Api(value = "Example api for testing callback", description = "API endpoint for testing callbacks")
public class ExampleResource {

    Client client = ClientBuilder.newBuilder().build();

    @Path("/v1/api/async")
    @POST
    @Metered
    @ApiOperation(value = "A dummy api simulating a async endpoint")
    public Response async() {
        log.info("Async call triggered");
        return Response.accepted().build();
    }

    @Path("/v1/api/trigger")
    @POST
    @Metered
    @ApiOperation(value = "Trigger a callback")
    public Response trigger() {
        val data = ImmutableMap.builder()
                .put("message", "test").build();
        return client.target("http://localhost:8080/revolver/v1/callback/test").request().post(Entity.json(data));
    }

    @Path("/v1/api/callback")
    @POST
    @Metered
    @ApiOperation(value = "Callback")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response callback() throws IOException {
        log.info("Callback triggered");
        return Response.accepted().build();
    }



}
