package org.openstack4j.openstack.telemetry.domain;

import com.google.common.base.Objects;
import org.codehaus.jackson.annotate.JsonProperty;
import org.openstack4j.model.telemetry.Resource;
import org.openstack4j.openstack.common.GenericLink;
import org.openstack4j.openstack.compute.domain.MetaDataWrapper;

import java.util.List;

/**
 * Created by kuiluo on 14-7-28.
 */
public class CeilometerResource implements Resource {

    private List<GenericLink> links;

    private MetaDataWrapper metadata;

    @JsonProperty("project_id")
    private String projectId;

    @JsonProperty("resource_id")
    private String resourceId;

    private String source;

    @JsonProperty("user_id")
    private String userId;


    @Override
    public List<GenericLink> getLinks() {
        return this.links;
    }

    @Override
    public MetaDataWrapper getMetadata() {
        return this.metadata;
    }

    @Override
    public String getProjectId() {
        return this.projectId;
    }

    @Override
    public String getResourceId() {
        return this.resourceId;
    }

    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(this).omitNullValues()
                .add("links", links).add("metadata", metadata)
                .add("project_id", projectId)
                .add("resource_id", resourceId)
                .add("source", source)
                .add("user_id", userId)
                .toString();
    }

}
