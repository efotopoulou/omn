package info.openmultinet.ontology.translators.geni.manifest;

import info.openmultinet.ontology.translators.AbstractConverter;
import info.openmultinet.ontology.translators.geni.CommonMethods;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.AccessNetwork;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.ControlAddressContents;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.EpcIpContents;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.GeniSliceInfo;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.GeniSliverInfo;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.LinkContents;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.Monitoring;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.NodeContents;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.ObjectFactory;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.Proxy;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.RSpecContents;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.Reservation;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.ServiceContents;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.ServicesPostBootScript;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.Ue;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.UeDiskImageContents;
import info.openmultinet.ontology.translators.geni.jaxb.manifest.UeHardwareTypeContents;
import info.openmultinet.ontology.vocabulary.Omn;
import info.openmultinet.ontology.vocabulary.Omn_domain_pc;
import info.openmultinet.ontology.vocabulary.Omn_lifecycle;
import info.openmultinet.ontology.vocabulary.Omn_service;
import info.openmultinet.ontology.vocabulary.Osco;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.datatype.XMLGregorianCalendar;

import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * Helper methods for converting from OMN model to manifest RSpec. These methods
 * are for non-native RSpec extensions.
 * 
 * @author robynloughnane
 *
 */
public class ManifestSetExt extends AbstractConverter {

	private static final String HOST = "http://open-multinet.info/example#";
	private static final Logger LOG = Logger.getLogger(ManifestSetExt.class
			.getName());

	public static void setOsco(Statement resource, NodeContents node) {
		Resource resourceResource = resource.getResource();

		// check whether file has any osco properties
		if (resourceResource.hasProperty(Osco.APP_PORT)
				|| resourceResource.hasProperty(Osco.LOGGING_FILE)
				|| resourceResource.hasProperty(Osco.REQUIRE_AUTH)
				|| resourceResource.hasProperty(Osco.SERVICE_PORT)) {

			info.openmultinet.ontology.translators.geni.jaxb.manifest.Osco osco = new ObjectFactory()
					.createOsco();

			if (resourceResource.hasProperty(Osco.APP_PORT)) {
				String appPort = resourceResource.getProperty(Osco.APP_PORT)
						.getObject().asLiteral().getString();
				osco.setAppPort(appPort);
			}

			if (resourceResource.hasProperty(Osco.LOGGING_FILE)) {
				String loggingFile = resourceResource
						.getProperty(Osco.LOGGING_FILE).getObject().asLiteral()
						.getString();
				osco.setLoggingFile(loggingFile);
			}

			if (resourceResource.hasProperty(Osco.REQUIRE_AUTH)) {
				String requireAuthString = resourceResource
						.getProperty(Osco.REQUIRE_AUTH).getObject().asLiteral()
						.getString();
				boolean requireAuth = Boolean.parseBoolean(requireAuthString);
				osco.setRequireAuth(requireAuth);
			}

			if (resourceResource.hasProperty(Osco.SERVICE_PORT)) {
				String servicePort = resourceResource
						.getProperty(Osco.SERVICE_PORT).getObject().asLiteral()
						.getString();
				osco.setServicePort(servicePort);
			}

			if (resourceResource.hasProperty(Osco.requires)) {
				String requires = resourceResource.getProperty(Osco.requires)
						.getObject().asResource().getURI();
				osco.setRequires(requires);
			}

			if (resourceResource.hasProperty(Osco.NOTIFY_DISABLED)) {
				String notifyDisabledString = resourceResource
						.getProperty(Osco.NOTIFY_DISABLED).getObject()
						.asLiteral().getString();
				boolean notifyDisabled = Boolean
						.parseBoolean(notifyDisabledString);
				osco.setNotifyDisabled(notifyDisabled);
			}

			if (resourceResource.hasProperty(Osco.RETARGET_DISABLED)) {
				String retargetDisabledString = resourceResource
						.getProperty(Osco.RETARGET_DISABLED).getObject()
						.asLiteral().getString();
				boolean retargetDisabled = Boolean
						.parseBoolean(retargetDisabledString);
				osco.setRetargetDisabled(retargetDisabled);
			}

			if (resourceResource.hasProperty(Osco.LOGGING_LEVEL)) {
				String loggingLevel = resourceResource
						.getProperty(Osco.LOGGING_LEVEL).getObject()
						.asLiteral().getString();
				osco.setLoggingLevel(loggingLevel);
			}

			if (resourceResource.hasProperty(Osco.NOTIFY_CHAN_DISABLED)) {
				String notifyChanDisabledString = resourceResource
						.getProperty(Osco.NOTIFY_CHAN_DISABLED).getObject()
						.asLiteral().getString();
				boolean notifyChanDisabled = Boolean
						.parseBoolean(notifyChanDisabledString);
				osco.setNotifyChanDisabled(notifyChanDisabled);
			}

			if (resourceResource.hasProperty(Osco.COAP_DISABLED)) {
				String coapDisabledString = resourceResource
						.getProperty(Osco.COAP_DISABLED).getObject()
						.asLiteral().getString();
				boolean coapDisabled = Boolean.parseBoolean(coapDisabledString);
				osco.setCoapDisabled(coapDisabled);
			}

			if (resourceResource.hasProperty(Osco.ANNC_AUTO)) {
				String anncAutoString = resourceResource
						.getProperty(Osco.ANNC_AUTO).getObject().asLiteral()
						.getString();
				boolean anncAuto = Boolean.parseBoolean(anncAutoString);
				osco.setAnncAuto(anncAuto);
			}

			if (resourceResource.hasProperty(Osco.MGMT_INTF)) {
				String mgmtIntf = resourceResource.getProperty(Osco.MGMT_INTF)
						.getObject().asLiteral().getString();
				osco.setMgmtIntf(mgmtIntf);
			}

			if (resourceResource.hasProperty(Osco.ANNC_DISABLED)) {
				String anncDisabledString = resourceResource
						.getProperty(Osco.ANNC_DISABLED).getObject()
						.asLiteral().getString();
				boolean anncDisabled = Boolean.parseBoolean(anncDisabledString);
				osco.setAnncDisabled(anncDisabled);
			}

			node.getAnyOrRelationOrLocation().add(osco);
		}

	}

	public static void setReservation(Statement omnResource, NodeContents node) {

		List<Statement> reservations = omnResource.getResource()
				.listProperties(Omn.hasReservation).toList();

		for (final Statement reservationStatement : reservations) {
			Resource reservationResource = reservationStatement.getObject()
					.asResource();

			Reservation reservation = null;
			if (reservationResource.hasProperty(Omn_lifecycle.expirationTime)) {
				if (reservation == null) {
					reservation = new ObjectFactory().createReservation();
				}

				XSDDateTime expTime = (XSDDateTime) reservationResource
						.getProperty(Omn_lifecycle.expirationTime).getObject()
						.asLiteral().getValue();
				XMLGregorianCalendar xgc = xsdToXmlTime(expTime);

				if (xgc != null) {
					reservation.setExpirationTime(xgc);
				}

			}

			if (reservationResource
					.hasProperty(Omn_lifecycle.hasReservationState)) {
				if (reservation == null) {
					reservation = new ObjectFactory().createReservation();
				}

				Resource state = reservationResource
						.getProperty(Omn_lifecycle.hasReservationState)
						.getObject().asResource();

				String stateGeni = CommonMethods.convertOmnToGeniState(state);
				reservation.setReservationState(stateGeni);
			}

			if (reservation != null) {
				node.getAnyOrRelationOrLocation().add(reservation);
			}
		}
	}

	public static void setGeniSliceInfo(Resource group, RSpecContents manifest) {

		GeniSliceInfo geniSliceInfo = null;
		Resource omnResource = group;

		if (omnResource.hasProperty(Omn_lifecycle.hasState)) {
			if (geniSliceInfo == null) {
				geniSliceInfo = new ObjectFactory().createGeniSliceInfo();
			}
			Resource state = omnResource.getProperty(Omn_lifecycle.hasState)
					.getObject().asResource();

			String stateGeni = CommonMethods.convertOmnToGeniState(state);
			if (stateGeni != null && stateGeni != "") {
				geniSliceInfo.setState(stateGeni);
			}
		}

		if (omnResource.hasProperty(Omn_domain_pc.hasUUID)) {
			geniSliceInfo.setUuid(omnResource
					.getProperty(Omn_domain_pc.hasUUID).getObject().asLiteral()
					.getString());
		}

		if (omnResource.hasProperty(Omn.hasURI)) {
			geniSliceInfo.setUrn(omnResource.getProperty(Omn.hasURI)
					.getObject().asLiteral().getString());
		}

		if (geniSliceInfo != null) {
			manifest.getAnyOrNodeOrLink().add(geniSliceInfo);
		}

	}

	public static void setGeniSliverInfo(Statement resource, Object node) {

		GeniSliverInfo geniSliverInfo = null;
		Resource omnResource = resource.getResource();
		if (omnResource.hasProperty(Omn_lifecycle.creator)) {
			geniSliverInfo = new ObjectFactory().createGeniSliverInfo();
			geniSliverInfo.setCreatorUrn(omnResource
					.getProperty(Omn_lifecycle.creator).getObject().asLiteral()
					.getString());
		}

		if (resource.getResource().hasProperty(Omn_lifecycle.creationTime)) {
			if (geniSliverInfo == null) {
				geniSliverInfo = new ObjectFactory().createGeniSliverInfo();
			}

			XSDDateTime creationTime = (XSDDateTime) omnResource
					.getProperty(Omn_lifecycle.creationTime).getObject()
					.asLiteral().getValue();
			XMLGregorianCalendar xgc = xsdToXmlTime(creationTime);

			if (xgc != null) {
				geniSliverInfo.setCreationTime(xgc);
			}
		}

		if (omnResource.hasProperty(Omn_lifecycle.startTime)) {
			if (geniSliverInfo == null) {
				geniSliverInfo = new ObjectFactory().createGeniSliverInfo();
			}

			XSDDateTime startTime = (XSDDateTime) omnResource
					.getProperty(Omn_lifecycle.startTime).getObject()
					.asLiteral().getValue();
			XMLGregorianCalendar xgc = xsdToXmlTime(startTime);

			if (xgc != null) {
				geniSliverInfo.setStartTime(xgc);
			}
		}

		if (omnResource.hasProperty(Omn_lifecycle.expirationTime)) {
			if (geniSliverInfo == null) {
				geniSliverInfo = new ObjectFactory().createGeniSliverInfo();
			}

			XSDDateTime expTime = (XSDDateTime) omnResource
					.getProperty(Omn_lifecycle.expirationTime).getObject()
					.asLiteral().getValue();
			XMLGregorianCalendar xgc = xsdToXmlTime(expTime);

			if (xgc != null) {
				geniSliverInfo.setExpirationTime(xgc);
			}
		}

		if (omnResource.hasProperty(Omn_lifecycle.resourceId)) {
			if (geniSliverInfo == null) {
				geniSliverInfo = new ObjectFactory().createGeniSliverInfo();
			}
			geniSliverInfo.setResourceId(omnResource
					.getProperty(Omn_lifecycle.resourceId).getObject()
					.asLiteral().getString());
		}

		if (omnResource.hasProperty(Omn_lifecycle.hasState)) {
			if (geniSliverInfo == null) {
				geniSliverInfo = new ObjectFactory().createGeniSliverInfo();
			}
			Resource state = omnResource.getProperty(Omn_lifecycle.hasState)
					.getObject().asResource();

			String stateGeni = CommonMethods.convertOmnToGeniState(state);
			if (stateGeni != null && stateGeni != "") {
				geniSliverInfo.setState(stateGeni);
			}
		}

		if (geniSliverInfo != null) {
			if (node.getClass().equals(NodeContents.class)) {
				((NodeContents) node).getAnyOrRelationOrLocation().add(
						geniSliverInfo);
			}
			if (node.getClass().equals(LinkContents.class)) {
				((LinkContents) node).getAnyOrPropertyOrLinkType().add(
						geniSliverInfo);
			}
		}
	}

	public static void setMonitoringService(Statement resource,
			NodeContents node) {
		Resource resourceResource = resource.getResource();
		if (resourceResource.hasProperty(Omn_lifecycle.usesService)) {
			Statement monitoringService = resourceResource
					.getProperty(Omn_lifecycle.usesService);
			Resource monitoringResource = monitoringService.getResource();
			Monitoring monitoring = new ObjectFactory().createMonitoring();

			if (monitoringResource.hasProperty(Omn.hasURI)) {
				Statement hasUri = monitoringService.getResource().getProperty(
						Omn.hasURI);

				String uri = hasUri.getObject().asLiteral().getString();
				monitoring.setUri(uri);
			}

			if (monitoringResource.hasProperty(RDF.type)) {
				final List<Statement> hasTypes = monitoringResource
						.listProperties(RDF.type).toList();

				for (final Statement hasType : hasTypes) {

					String type = hasType.getResource().getURI();

					if (AbstractConverter.nonGeneric(type)) {
						monitoring.setType(type);
					}
				}
			}

			node.getAnyOrRelationOrLocation().add(monitoring);
		}
	}

	static void setProxyService(Resource serviceResource,
			ServiceContents serviceContents) {
		if (serviceResource.hasProperty(RDF.type, Omn_service.Proxy)) {

			// get proxy
			String proxyProxy = "";
			if (serviceResource.hasProperty(Omn_service.proxyProxy)) {
				proxyProxy += serviceResource
						.getProperty(Omn_service.proxyProxy).getObject()
						.asLiteral().getString();
			}

			// get for
			String proxyFor = "";
			if (serviceResource.hasProperty(Omn_service.proxyFor)) {
				proxyFor += serviceResource.getProperty(Omn_service.proxyFor)
						.getObject().asLiteral().getString();
			}

			// create execute
			Proxy proxy = new ObjectFactory().createProxy();

			proxy.setProxy(proxyProxy);

			if (proxyFor != "") {
				proxy.setFor(proxyFor);
			}

			serviceContents.getAnyOrLoginOrInstall().add(proxy);
		}

	}

	static void setPostBootScriptService(Resource serviceResource,
			ServiceContents serviceContents) {

		if (serviceResource.hasProperty(RDF.type, Omn_service.PostBootScript)) {

			// get type
			String type = "";
			if (serviceResource.hasProperty(Omn_service.postBootScriptType)) {
				type += serviceResource
						.getProperty(Omn_service.postBootScriptType)
						.getObject().asLiteral().getString();
			}

			// get text
			String text = "";
			if (serviceResource.hasProperty(Omn_service.postBootScriptText)) {
				text += serviceResource
						.getProperty(Omn_service.postBootScriptText)
						.getObject().asLiteral().getString();
			}

			// create execute
			ServicesPostBootScript servicesPostBootScript = new ObjectFactory()
					.createServicesPostBootScript();

			servicesPostBootScript.setType(type);

			if (text != "") {
				servicesPostBootScript.setContent(text);
			}

			serviceContents.getAnyOrLoginOrInstall()
					.add(servicesPostBootScript);
		}
	}

	public static void setEPC(Statement omnResource, NodeContents geniNode) {

		// Resource resourceResource = omnResource.getResource();
		// if (resourceResource.hasProperty(RDF.type,
		// info.openmultinet.ontology.vocabulary.Epc.EvolvedPacketCore)) {

		if (omnResource.getResource().hasProperty(
				info.openmultinet.ontology.vocabulary.Epc.hasEvolvedPacketCore)) {

			Resource resourceResource = omnResource
					.getProperty(
							info.openmultinet.ontology.vocabulary.Epc.hasEvolvedPacketCore)
					.getObject().asResource();

			info.openmultinet.ontology.translators.geni.jaxb.manifest.Epc epc = new ObjectFactory()
					.createEpc();

			if (resourceResource
					.hasProperty(info.openmultinet.ontology.vocabulary.Epc.mmeAddress)) {
				String mmeAddress = resourceResource
						.getProperty(
								info.openmultinet.ontology.vocabulary.Epc.mmeAddress)
						.getObject().asLiteral().getString();
				epc.setMmeAddress(mmeAddress);
			}

			if (resourceResource
					.hasProperty(info.openmultinet.ontology.vocabulary.Epc.pdnGateway)) {
				String pdnGateway = resourceResource
						.getProperty(
								info.openmultinet.ontology.vocabulary.Epc.pdnGateway)
						.getObject().asLiteral().getString();
				epc.setPdnGateway(pdnGateway);
			}

			if (resourceResource
					.hasProperty(info.openmultinet.ontology.vocabulary.Epc.servingGateway)) {
				String servingGateway = resourceResource
						.getProperty(
								info.openmultinet.ontology.vocabulary.Epc.servingGateway)
						.getObject().asLiteral().getString();
				epc.setServingGateway(servingGateway);
			}

			if (resourceResource
					.hasProperty(info.openmultinet.ontology.vocabulary.Epc.vendor)) {
				String vendor = resourceResource
						.getProperty(
								info.openmultinet.ontology.vocabulary.Epc.vendor)
						.getObject().asLiteral().getString();
				epc.setVendor(vendor);
			}

			StmtIterator apns = resourceResource
					.listProperties(info.openmultinet.ontology.vocabulary.Epc.hasAccessPointName);
			while (apns.hasNext()) {
				Statement apnStatement = apns.next();
				Resource apn = apnStatement.getObject().asResource();
				epc.getApnOrEnodebOrSubscriber().add(setEpcApn(apn));
			}

			StmtIterator eNodeBs = resourceResource
					.listProperties(info.openmultinet.ontology.vocabulary.Epc.hasENodeB);
			while (eNodeBs.hasNext()) {
				Statement eNodeBStatement = eNodeBs.next();
				Resource eNodeB = eNodeBStatement.getObject().asResource();
				setENodeB(epc, eNodeB);
			}

			StmtIterator subscribers = resourceResource
					.listProperties(info.openmultinet.ontology.vocabulary.Epc.subscriber);
			while (subscribers.hasNext()) {
				Statement subscriberStatement = subscribers.next();
				String subscriber = subscriberStatement.getObject().asLiteral()
						.getString();
				info.openmultinet.ontology.translators.geni.jaxb.manifest.SubscriberContents subscriberContents = new ObjectFactory()
						.createSubscriberContents();
				subscriberContents.setImsiNumber(subscriber);
				epc.getApnOrEnodebOrSubscriber().add(subscriberContents);
			}

			geniNode.getAnyOrRelationOrLocation().add(epc);
		}
	}

	private static void setENodeB(
			info.openmultinet.ontology.translators.geni.jaxb.manifest.Epc epc,
			Resource eNodeB) {
		info.openmultinet.ontology.translators.geni.jaxb.manifest.ENodeBContents eNodeBContents = new ObjectFactory()
				.createENodeBContents();

		if (eNodeB
				.hasProperty(info.openmultinet.ontology.vocabulary.Epc.eNodeBAddress)) {
			String address = eNodeB
					.getProperty(
							info.openmultinet.ontology.vocabulary.Epc.eNodeBAddress)
					.getObject().asLiteral().getString();
			eNodeBContents.setAddress(address);
		}

		if (eNodeB
				.hasProperty(info.openmultinet.ontology.vocabulary.Epc.eNodeBName)) {
			String name = eNodeB
					.getProperty(
							info.openmultinet.ontology.vocabulary.Epc.eNodeBName)
					.getObject().asLiteral().getString();
			eNodeBContents.setName(name);
		}

		epc.getApnOrEnodebOrSubscriber().add(eNodeBContents);

	}

	private static info.openmultinet.ontology.translators.geni.jaxb.manifest.ApnContents setEpcApn(
			Resource apn) {
		info.openmultinet.ontology.translators.geni.jaxb.manifest.ApnContents apnContents = new ObjectFactory()
				.createApnContents();

		if (apn.hasProperty(info.openmultinet.ontology.vocabulary.Epc.networkIdentifier)) {
			String networkId = apn
					.getProperty(
							info.openmultinet.ontology.vocabulary.Epc.networkIdentifier)
					.getObject().asLiteral().getString();
			apnContents.setNetworkId(networkId);
		}

		if (apn.hasProperty(info.openmultinet.ontology.vocabulary.Epc.operatorIdentifier)) {
			String operatorId = apn
					.getProperty(
							info.openmultinet.ontology.vocabulary.Epc.operatorIdentifier)
					.getObject().asLiteral().getString();
			apnContents.setOperatorId(operatorId);
		}

		return apnContents;

	}

	public static void setAccessNetwork(Statement omnResource,
			NodeContents geniNode) {
		if (omnResource.getResource().hasProperty(
				info.openmultinet.ontology.vocabulary.Epc.hasAccessNetwork)) {

			Resource resourceResource = omnResource
					.getProperty(
							info.openmultinet.ontology.vocabulary.Epc.hasAccessNetwork)
					.getObject().asResource();

			AccessNetwork accessNetwork = new ObjectFactory()
					.createAccessNetwork();

			if (resourceResource
					.hasProperty(info.openmultinet.ontology.vocabulary.Epc.eNodeBId)) {
				String eNodeBId = resourceResource
						.getProperty(
								info.openmultinet.ontology.vocabulary.Epc.eNodeBId)
						.getObject().asLiteral().getString();
				accessNetwork.setEnodebId(eNodeBId);
			}

			if (resourceResource
					.hasProperty(info.openmultinet.ontology.vocabulary.Epc.publicLandMobileNetworkId)) {
				String publicLandMobileNetworkId = resourceResource
						.getProperty(
								info.openmultinet.ontology.vocabulary.Epc.publicLandMobileNetworkId)
						.getObject().asLiteral().getString();
				accessNetwork.setPlmnId(publicLandMobileNetworkId);
			}

			if (resourceResource
					.hasProperty(info.openmultinet.ontology.vocabulary.Epc.band)) {
				String band = resourceResource
						.getProperty(
								info.openmultinet.ontology.vocabulary.Epc.band)
						.getObject().asLiteral().getString();
				int bandInt = Integer.parseInt(band);
				BigInteger bandBigInt = BigInteger.valueOf(bandInt);
				accessNetwork.setBand(bandBigInt);
			}

			if (resourceResource
					.hasProperty(info.openmultinet.ontology.vocabulary.Epc.vendor)) {
				String vendor = resourceResource
						.getProperty(
								info.openmultinet.ontology.vocabulary.Epc.vendor)
						.getObject().asLiteral().getString();
				accessNetwork.setVendor(vendor);
			}

			if (resourceResource
					.hasProperty(info.openmultinet.ontology.vocabulary.Epc.baseModel)) {
				String baseModel = resourceResource
						.getProperty(
								info.openmultinet.ontology.vocabulary.Epc.baseModel)
						.getObject().asLiteral().getString();
				accessNetwork.setBaseModel(baseModel);
			}

			if (resourceResource
					.hasProperty(info.openmultinet.ontology.vocabulary.Epc.evolvedPacketCoreAddress)) {
				String evolvedPacketCoreAddress = resourceResource
						.getProperty(
								info.openmultinet.ontology.vocabulary.Epc.evolvedPacketCoreAddress)
						.getObject().asLiteral().getString();
				accessNetwork.setEpcAddress(evolvedPacketCoreAddress);
			}

			if (resourceResource
					.hasProperty(info.openmultinet.ontology.vocabulary.Epc.mode)) {
				String mode = resourceResource
						.getProperty(
								info.openmultinet.ontology.vocabulary.Epc.mode)
						.getObject().asLiteral().getString();
				accessNetwork.setMode(mode);
			}

			StmtIterator apns = resourceResource
					.listProperties(info.openmultinet.ontology.vocabulary.Epc.hasAccessPointName);
			while (apns.hasNext()) {
				Statement apnStatement = apns.next();
				Resource apn = apnStatement.getObject().asResource();
				// accessNetwork.getApn().add(setEpcApn(apn));
				accessNetwork.getApnOrIpAddress().add(setEpcApn(apn));
			}

			StmtIterator ipAddresses = resourceResource
					.listProperties(info.openmultinet.ontology.vocabulary.Omn_resource.hasIPAddress);
			while (ipAddresses.hasNext()) {
				Statement ipAddressStatement = ipAddresses.next();
				Resource ipAddress = ipAddressStatement.getObject()
						.asResource();
				setEpcIpAddress(accessNetwork, ipAddress);
			}

			geniNode.getAnyOrRelationOrLocation().add(accessNetwork);
		}

	}

	private static void setEpcIpAddress(AccessNetwork accessNetwork,
			Resource ipAddress) {
		ObjectFactory objectFactory = new ObjectFactory();
		EpcIpContents controlAddressContents = objectFactory
				.createEpcIpContents();

		if (ipAddress
				.hasProperty(info.openmultinet.ontology.vocabulary.Omn_resource.address)) {
			String address = ipAddress
					.getProperty(
							info.openmultinet.ontology.vocabulary.Omn_resource.address)
					.getObject().asLiteral().getString();
			controlAddressContents.setAddress(address);
		}

		if (ipAddress
				.hasProperty(info.openmultinet.ontology.vocabulary.Omn_resource.netmask)) {
			String netmask = ipAddress
					.getProperty(
							info.openmultinet.ontology.vocabulary.Omn_resource.netmask)
					.getObject().asLiteral().getString();
			controlAddressContents.setNetmask(netmask);
		}

		if (ipAddress
				.hasProperty(info.openmultinet.ontology.vocabulary.Omn_resource.type)) {
			String type = ipAddress
					.getProperty(
							info.openmultinet.ontology.vocabulary.Omn_resource.type)
					.getObject().asLiteral().getString();
			controlAddressContents.setType(type);
		}

		accessNetwork.getApnOrIpAddress().add(controlAddressContents);

	}

	public static void setUserEquipment(Statement omnResource,
			NodeContents geniNode) {
		if (omnResource.getResource().hasProperty(
				info.openmultinet.ontology.vocabulary.Epc.hasUserEquipment)) {

			Resource resourceResource = omnResource
					.getProperty(
							info.openmultinet.ontology.vocabulary.Epc.hasUserEquipment)
					.getObject().asResource();

			Ue ue = new ObjectFactory().createUe();

			if (resourceResource
					.hasProperty(info.openmultinet.ontology.vocabulary.Epc.lteSupport)) {
				String lteSuport = resourceResource
						.getProperty(
								info.openmultinet.ontology.vocabulary.Epc.lteSupport)
						.getObject().asLiteral().getString();
				boolean lteSuportBool = Boolean.parseBoolean(lteSuport);
				ue.setLteSupport(lteSuportBool);
			}

			StmtIterator apns = resourceResource
					.listProperties(info.openmultinet.ontology.vocabulary.Epc.hasAccessPointName);
			while (apns.hasNext()) {
				Statement apnStatement = apns.next();
				Resource apn = apnStatement.getObject().asResource();
				ue.getApnOrControlAddressOrUeHardwareType().add(setEpcApn(apn));
			}

			StmtIterator controlAddresses = resourceResource
					.listProperties(info.openmultinet.ontology.vocabulary.Epc.hasControlAddress);
			while (controlAddresses.hasNext()) {
				Statement controlAddressStatement = controlAddresses.next();
				Resource controlAddressResource = controlAddressStatement
						.getObject().asResource();
				setControlAddress(controlAddressResource, ue);
			}

			StmtIterator hardwareTypes = resourceResource
					.listProperties(info.openmultinet.ontology.vocabulary.Omn_resource.hasHardwareType);
			while (hardwareTypes.hasNext()) {
				Statement hardwareTypeStatement = hardwareTypes.next();
				Resource hardwareTypeResource = hardwareTypeStatement
						.getObject().asResource();
				setEpcHardwareType(hardwareTypeResource, ue);
			}

			StmtIterator diskImages = resourceResource
					.listProperties(info.openmultinet.ontology.vocabulary.Omn_domain_pc.hasDiskImage);
			while (diskImages.hasNext()) {
				Statement diskImageStatement = diskImages.next();
				Resource diskImageResource = diskImageStatement.getObject()
						.asResource();
				setUeDiskImage(diskImageResource, ue);
			}

			geniNode.getAnyOrRelationOrLocation().add(ue);
		}

	}

	private static void setUeDiskImage(Resource diskImageResource, Ue ue) {
		ObjectFactory objectFactory = new ObjectFactory();
		UeDiskImageContents diskImageContents = objectFactory
				.createUeDiskImageContents();

		if (diskImageResource.hasProperty(Omn_domain_pc.hasDiskimageLabel)) {
			String name = diskImageResource
					.getProperty(Omn_domain_pc.hasDiskimageLabel).getObject()
					.asLiteral().getString();
			diskImageContents.setName(name);
		}

		if (diskImageResource
				.hasProperty(Omn_domain_pc.hasDiskimageDescription)) {
			String description = diskImageResource
					.getProperty(Omn_domain_pc.hasDiskimageDescription)
					.getObject().asLiteral().getString();
			diskImageContents.setDescription(description);
		}

		ue.getApnOrControlAddressOrUeHardwareType().add(diskImageContents);

	}

	private static void setEpcHardwareType(Resource hardwareTypeResource, Ue ue) {

		ObjectFactory objectFactory = new ObjectFactory();
		UeHardwareTypeContents hardwareTypeContents = objectFactory
				.createUeHardwareTypeContents();

		if (hardwareTypeResource.hasProperty(RDFS.label)) {
			String name = hardwareTypeResource.getProperty(RDFS.label)
					.getObject().asLiteral().getString();
			hardwareTypeContents.setName(name);
		}

		ue.getApnOrControlAddressOrUeHardwareType().add(hardwareTypeContents);

	}

	private static void setControlAddress(Resource controlAddressResource, Ue ue) {
		ObjectFactory objectFactory = new ObjectFactory();
		ControlAddressContents controlAddressContents = objectFactory
				.createControlAddressContents();

		if (controlAddressResource
				.hasProperty(info.openmultinet.ontology.vocabulary.Omn_resource.address)) {
			String address = controlAddressResource
					.getProperty(
							info.openmultinet.ontology.vocabulary.Omn_resource.address)
					.getObject().asLiteral().getString();
			controlAddressContents.setAddress(address);
		}

		if (controlAddressResource
				.hasProperty(info.openmultinet.ontology.vocabulary.Omn_resource.netmask)) {
			String netmask = controlAddressResource
					.getProperty(
							info.openmultinet.ontology.vocabulary.Omn_resource.netmask)
					.getObject().asLiteral().getString();
			controlAddressContents.setNetmask(netmask);
		}

		if (controlAddressResource
				.hasProperty(info.openmultinet.ontology.vocabulary.Omn_resource.type)) {
			String type = controlAddressResource
					.getProperty(
							info.openmultinet.ontology.vocabulary.Omn_resource.type)
					.getObject().asLiteral().getString();
			controlAddressContents.setType(type);
		}

		ue.getApnOrControlAddressOrUeHardwareType().add(controlAddressContents);

	}
}