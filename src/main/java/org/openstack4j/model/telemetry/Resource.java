package org.openstack4j.model.telemetry;

import org.openstack4j.model.ModelEntity;
import org.openstack4j.model.common.Link;
import org.openstack4j.openstack.compute.domain.MetaDataWrapper;

import java.util.List;

/**
 * Created by kuiluo on 14-7-28.
 */
public interface Resource extends ModelEntity{

    List<? extends Link> getLinks();

    MetaDataWrapper getMetadata();

    String getProjectId();

    String getResourceId();

    String getSource();

    String getUserId();
}
