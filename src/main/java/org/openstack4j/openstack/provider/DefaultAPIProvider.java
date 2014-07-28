package org.openstack4j.openstack.provider;

import com.google.common.collect.Maps;
import org.openstack4j.api.APIProvider;
import org.openstack4j.api.compute.*;
import org.openstack4j.api.exceptions.ApiNotFoundException;
import org.openstack4j.api.identity.*;
import org.openstack4j.api.image.ImageService;
import org.openstack4j.api.networking.*;
import org.openstack4j.api.storage.BlockStorageService;
import org.openstack4j.api.storage.BlockVolumeService;
import org.openstack4j.api.storage.BlockVolumeSnapshotService;
import org.openstack4j.api.telemetry.CollectorService;
import org.openstack4j.api.telemetry.MeterService;
import org.openstack4j.api.telemetry.TelemetryService;
import org.openstack4j.openstack.compute.internal.*;
import org.openstack4j.openstack.identity.internal.*;
import org.openstack4j.openstack.image.internal.ImageServiceImpl;
import org.openstack4j.openstack.networking.internal.*;
import org.openstack4j.openstack.storage.block.internal.BlockStorageServiceImpl;
import org.openstack4j.openstack.storage.block.internal.BlockVolumeServiceImpl;
import org.openstack4j.openstack.storage.block.internal.BlockVolumeSnapshotServiceImpl;
import org.openstack4j.openstack.telemetry.internal.CollectorServiceImpl;
import org.openstack4j.openstack.telemetry.internal.MeterServiceImpl;
import org.openstack4j.openstack.telemetry.internal.TelemetryServiceImpl;

import java.util.Map;

/**
 * Simple API Provider which keeps internally Maps interface implementations as singletons
 *
 * @author Jeremy Unruh
 */
public class DefaultAPIProvider implements APIProvider {

    private static final Map<Class<?>, Class<?>> bindings = Maps.newHashMap();
    private static final Map<Class<?>, Object> instances = Maps.newConcurrentMap();

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        bind(IdentityService.class, IdentityServiceImpl.class);
        bind(TenantService.class, TenantServiceImpl.class);
        bind(UserService.class, UserServiceImpl.class);
        bind(RoleService.class, RoleServiceImpl.class);
        bind(ServiceManagerService.class, ServiceManagerServiceImpl.class);
        bind(ComputeService.class, ComputeServiceImpl.class);
        bind(FlavorService.class, FlavorServiceImpl.class);
        bind(ComputeImageService.class, ComputeImageServiceImpl.class);
        bind(ServerService.class, ServerServiceImpl.class);
        bind(QuotaSetService.class, QuotaSetServiceImpl.class);
        bind(NetworkingService.class, NetworkingServiceImpl.class);
        bind(NetworkService.class, NetworkServiceImpl.class);
        bind(SubnetService.class, SubnetServiceImpl.class);
        bind(PortService.class, PortServiceImpl.class);
        bind(RouterService.class, RouterServiceImpl.class);
        bind(ImageService.class, ImageServiceImpl.class);
        bind(BlockStorageService.class, BlockStorageServiceImpl.class);
        bind(BlockVolumeService.class, BlockVolumeServiceImpl.class);
        bind(BlockVolumeSnapshotService.class, BlockVolumeSnapshotServiceImpl.class);
        bind(ComputeSecurityGroupService.class, ComputeSecurityGroupServiceImpl.class);
        bind(KeypairService.class, KeypairServiceImpl.class);
        bind(NetFloatingIPService.class, FloatingIPServiceImpl.class);
        bind(ComputeFloatingIPService.class, ComputeFloatingIPServiceImpl.class);
        bind(SecurityGroupService.class, SecurityGroupServiceImpl.class);
        bind(SecurityGroupRuleService.class, SecurityGroupRuleServiceImpl.class);
        bind(TelemetryService.class, TelemetryServiceImpl.class);
        bind(MeterService.class, MeterServiceImpl.class);
        bind(CollectorService.class, CollectorServiceImpl.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Class<T> api) {
        if (instances.containsKey(api))
            return (T) instances.get(api);

        if (bindings.containsKey(api)) {
            try {
                T impl = (T) bindings.get(api).newInstance();
                instances.put(api, impl);
                return impl;
            } catch (Exception e) {
                e.printStackTrace();
                throw new ApiNotFoundException("API Not found for: " + api.getName(), e);
            }
        }
        throw new ApiNotFoundException("API Not found for: " + api.getName());
    }

    private void bind(Class<?> api, Class<?> impl) {
        bindings.put(api, impl);
    }
}
