package org.opencloudengine.garuda.beluga.cloud;

import org.opencloudengine.garuda.beluga.exception.UnknownIaasProviderException;

import java.util.Properties;

/**
 * Created by swsong on 2015. 7. 15..
 */
public class IaasProvider {
    public static final String EC2_TYPE = "EC2";
    public static final String OPENSTACK_TYPE = "OPENSTACK";

    private String type;
    private String name;
    private String identity;
    private String credential;
    private String endPoint;
    private String region;
    private Properties overrides;

    public IaasProvider(String type, String name, String identity, String credential, String region, String endPoint) {
        this(type, name, identity, credential, endPoint, region, new Properties());
    }
    public IaasProvider(String type, String name, String identity, String credential, String endPoint, String region, Properties overrides) {
        this.type = type;
        this.name = name;
        this.identity = identity;
        this.credential = credential;
        this.endPoint = endPoint;
        this.region = region;
        this.overrides = overrides;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Properties getOverrides() {
        return overrides;
    }

    public void setOverrides(Properties overrides) {
        this.overrides = overrides;
    }

    public Iaas getIaas() throws UnknownIaasProviderException {
        if(type.equalsIgnoreCase(EC2_TYPE)) {
            return new EC2Iaas(endPoint, identity, credential, overrides);
        } else if(type.equalsIgnoreCase(OPENSTACK_TYPE)) {
                return new OpenstackIaas(endPoint, identity, credential, overrides);
        } else {
            throw new UnknownIaasProviderException("iaas provider type : " + type);
        }
    }

    @Override
    public String toString() {
        return String.format("IaasProvider type[%s] name[%s] endPoint[%s]", type, name, endPoint);
    }

}
