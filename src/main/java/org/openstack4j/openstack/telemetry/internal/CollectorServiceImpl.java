package org.openstack4j.openstack.telemetry.internal;

import org.openstack4j.api.telemetry.CollectorService;
import org.openstack4j.model.telemetry.Resource;
import org.openstack4j.openstack.telemetry.domain.CeilometerResource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by kuiluo on 14-7-28.
 */
public class CollectorServiceImpl extends BaseTelemetryServices implements CollectorService {

    @Override
    public List<? extends Resource> resources() {
        CeilometerResource[] resources = get(CeilometerResource[].class, uri("/resources")).execute();
        return wrapList(resources);
    }

    @Override
    public Resource resource(String resourceId) {
        checkNotNull(resourceId);

        CeilometerResource resource = get(CeilometerResource.class, uri("/resources/%s", resourceId)).execute();
        return resource;
    }
}
