package org.openstack4j.api.telemetry;

import org.openstack4j.common.RestService;
import org.openstack4j.model.telemetry.Resource;

import java.util.List;

/**
 * Created by kuiluo on 14-7-28.
 */
public interface CollectorService extends RestService {

    /**
     * Return all collectors
     * @return
     */
    List<? extends Resource> resources();


    /**
     * Return resource by resource id.
     * @param resourceId  The UUID of the resource.
     * @return
     */
    Resource resource(String resourceId);
}
