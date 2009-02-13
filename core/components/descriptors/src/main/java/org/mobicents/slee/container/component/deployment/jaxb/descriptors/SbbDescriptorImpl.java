/**
 * Start time:16:54:43 2009-01-19<br>
 * Project: mobicents-jainslee-server-core<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com">baranowb - Bartosz Baranowski
 *         </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
package org.mobicents.slee.container.component.deployment.jaxb.descriptors;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.slee.ComponentID;
import javax.slee.SbbID;
import javax.slee.management.DeployableUnitID;
import javax.slee.management.DeploymentException;
import javax.slee.management.LibraryID;

import org.mobicents.slee.container.component.deployment.jaxb.descriptors.common.MSecurityPermissions;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.common.MUsageParametersInterface;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.common.references.MEjbRef;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.common.references.MLibraryRef;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.common.references.MProfileSpecRef;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.common.references.MSbbRef;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.common.*;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.sbb.MActivityContextAttributeAlias;

import org.mobicents.slee.container.component.deployment.jaxb.descriptors.sbb.MEventEntry;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.sbb.MResourceAdaptorTypeBinding;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.sbb.MSbbAbstractClass;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.sbb.MSbbActivityContextInterface;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.sbb.MSbbLocalInterface;
import org.mobicents.slee.container.component.deployment.jaxb.slee.sbb.Sbb;
import org.mobicents.slee.container.component.deployment.jaxb.slee.sbb.SbbJar;
import org.mobicents.slee.container.component.deployment.jaxb.slee11.sbb.ActivityContextAttributeAlias;
import org.mobicents.slee.container.component.deployment.jaxb.slee11.sbb.EjbRef;
import org.mobicents.slee.container.component.deployment.jaxb.slee11.sbb.EnvEntry;
import org.mobicents.slee.container.component.deployment.jaxb.slee11.sbb.Event;
import org.mobicents.slee.container.component.deployment.jaxb.slee11.sbb.LibraryRef;
import org.mobicents.slee.container.component.deployment.jaxb.slee11.sbb.ResourceAdaptorTypeBinding;
import org.mobicents.slee.container.component.deployment.jaxb.slee11.sbb.SbbRef;
import org.w3c.dom.Document;

/**
 * Start time:16:54:43 2009-01-19<br>
 * Project: mobicents-jainslee-server-core<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com">baranowb - Bartosz Baranowski
 *         </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class SbbDescriptorImpl extends JAXBBaseUtilityClass {

	private SbbJar sbbJar = null;
	private org.mobicents.slee.container.component.deployment.jaxb.slee11.sbb.SbbJar llSbbJar = null;

	private Sbb sbb = null;
	private org.mobicents.slee.container.component.deployment.jaxb.slee11.sbb.Sbb llSbb = null;

	/**
	 * Index of this sbb in list generated by JXB
	 */
	private int index = -1;

	private String description = null;

	private SbbID sbbID = null;
	private String sbbAlias = null;
	// its 1.1
	private List<MSbbRef> sbbRefs = null;
	// Maybe this should be the same as in profiles as reference?
	private List<MProfileSpecRef> profileSpecRefs = null;

	// might be bad, we ommit sbb-classes/description, phew
	private MSbbAbstractClass sbbAbstractClass = null;
	private MSbbLocalInterface sbbLocalInterface = null;
	private MSbbActivityContextInterface sbbActivityContextInterface = null;
	private MUsageParametersInterface sbbUsageParametersInterface = null;
	private String addressProfileSpecAliasRef = null;
	private List<MEventEntry> events = null;
	private List<MActivityContextAttributeAlias> activityContextAttributeAliases = null;
	private List<MEnvEntry> envEntries = null;
	private List<MResourceAdaptorTypeBinding> resourceAdaptorTypeBindings = null;

	// 1.1 stuff, profile specs refs have alias element, so we need another.
	private Set<MLibraryRef> libraryRefs = null;
	private List<MEjbRef> ejbRefs = null;

	private MSecurityPermissions securityPermisions = null;

	private Set<ComponentID> dependenciesSet = new HashSet<ComponentID>();

	/**
	 * 
	 * @param doc
	 * @throws DeploymentException
	 */
	public SbbDescriptorImpl(Document doc) throws DeploymentException {
		super(doc);
		// TODO Auto-generated constructor stub
	}

	public SbbDescriptorImpl(Document doc, SbbJar sbbJar, int index)
			throws DeploymentException {
		super(doc);

		this.index = index;
		this.sbbJar = sbbJar;
		buildDescriptionMap();

	}

	public SbbDescriptorImpl(
			Document doc,
			org.mobicents.slee.container.component.deployment.jaxb.slee11.sbb.SbbJar sbbJar,
			int index) throws DeploymentException {
		super(doc);

		this.index = index;
		this.llSbbJar = sbbJar;
		buildDescriptionMap();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.mobicents.slee.container.component.deployment.jaxb.descriptors.
	 * JAXBBaseUtilityClass#buildDescriptionMap()
	 */
	@Override
	public void buildDescriptionMap() {
		if (isSlee11()) {
			this.llSbb = this.llSbbJar.getSbb().get(index);
			this.description = llSbb.getDescription() == null ? null
					: this.llSbb.getDescription().getvalue();

			this.sbbAlias = this.llSbb.getSbbAlias().getvalue();

			this.sbbID = new SbbID(this.llSbb.getSbbName().getvalue(),
					this.llSbb.getSbbVendor().getvalue(), this.llSbb
							.getSbbVersion().getvalue());

			// Library Refs
			this.libraryRefs = new HashSet<MLibraryRef>();
			if (llSbb.getLibraryRef() != null) {
				for (LibraryRef lr : this.llSbb.getLibraryRef()) {
					this.libraryRefs.add(new MLibraryRef(lr));
				}
			}

			// SbbRefs
			this.sbbRefs = new ArrayList<MSbbRef>();
			if (this.llSbb.getSbbRef() != null) {
				for (SbbRef sr : this.llSbb.getSbbRef()) {
					this.sbbRefs.add(new MSbbRef(sr));
				}
			}

			// Profile Refs
			this.profileSpecRefs = new ArrayList<MProfileSpecRef>();
			if (this.llSbb.getProfileSpecRef() != null) {
				for (org.mobicents.slee.container.component.deployment.jaxb.slee11.sbb.ProfileSpecRef psr : this.llSbb
						.getProfileSpecRef()) {
					// Second arg == Alias, its depraceted in 1.1
				  MProfileSpecRef p = new MProfileSpecRef(psr);
					this.profileSpecRefs.add(p);
				}
			}

			this.sbbAbstractClass = new MSbbAbstractClass(this.llSbb
					.getSbbClasses().getSbbAbstractClass());

			// Optional , 1.1
			if (this.llSbb.getSbbClasses().getSbbLocalInterface() != null) {
				this.sbbLocalInterface = new MSbbLocalInterface(this.llSbb
						.getSbbClasses().getSbbLocalInterface());
			}

			// Optional
			if (this.llSbb.getSbbClasses().getSbbActivityContextInterface() != null) {
				this.sbbActivityContextInterface = new MSbbActivityContextInterface(
						this.llSbb.getSbbClasses()
								.getSbbActivityContextInterface());
			}
			// Optional
			if (this.llSbb.getSbbClasses().getSbbUsageParametersInterface() != null) {
				this.sbbUsageParametersInterface = new MUsageParametersInterface(
						this.llSbb.getSbbClasses()
								.getSbbUsageParametersInterface());
			}

			// Optional
			if (this.llSbb.getAddressProfileSpecAliasRef() != null) {
				this.addressProfileSpecAliasRef = this.llSbb
						.getAddressProfileSpecAliasRef().getvalue();
			}

			this.events = new ArrayList<MEventEntry>();
			if (this.llSbb.getEvent() != null) {
				for (Event e : this.llSbb.getEvent()) {
					this.events.add(new MEventEntry(e));
				}
			}

			this.activityContextAttributeAliases = new ArrayList<MActivityContextAttributeAlias>();
			if (this.llSbb.getActivityContextAttributeAlias() != null) {
				for (ActivityContextAttributeAlias acaa : this.llSbb
						.getActivityContextAttributeAlias()) {
					this.activityContextAttributeAliases
							.add(new MActivityContextAttributeAlias(acaa));
				}
			}
			this.envEntries = new ArrayList<MEnvEntry>();
			if (this.llSbb.getEnvEntry() != null) {
				for (EnvEntry ee : this.llSbb.getEnvEntry()) {
					this.envEntries.add(new MEnvEntry(ee));
				}
			}

			this.resourceAdaptorTypeBindings = new ArrayList<MResourceAdaptorTypeBinding>();
			if (this.llSbb.getResourceAdaptorTypeBinding() != null) {
				for (ResourceAdaptorTypeBinding ratb : this.llSbb
						.getResourceAdaptorTypeBinding()) {
					this.resourceAdaptorTypeBindings
							.add(new MResourceAdaptorTypeBinding(ratb));
				}
			}

			this.ejbRefs = new ArrayList<MEjbRef>();
			if (this.llSbb.getEjbRef() != null) {
				for (EjbRef er : this.llSbb.getEjbRef()) {
					this.ejbRefs.add(new MEjbRef(er));
				}
			}
			if (this.llSbbJar.getSecurityPermissions() != null) {
				org.mobicents.slee.container.component.deployment.jaxb.slee11.sbb.SecurityPermissions secPerm = this.llSbbJar
						.getSecurityPermissions();
				this.securityPermisions = new MSecurityPermissions(secPerm
						.getDescription() == null ? null : secPerm
						.getDescription().getvalue(), secPerm
						.getSecurityPermissionSpec().getvalue());
			}

		} else {

			this.sbb = this.sbbJar.getSbb().get(index);
			this.description = sbb.getDescription() == null ? null : this.sbb
					.getDescription().getvalue();

			this.sbbAlias = this.sbb.getSbbAlias().getvalue();

			this.sbbID = new SbbID(this.sbb.getSbbName().getvalue(), this.sbb
					.getSbbVendor().getvalue(), this.sbb.getSbbVersion()
					.getvalue());
			// Library Refs
			this.libraryRefs = new HashSet<MLibraryRef>();

			// FIXME: template from jslee has child-sbb element, in dtd its
			// sbb-ref !!!!!!!
			// SbbRefs
			this.sbbRefs = new ArrayList<MSbbRef>();
			if (this.sbb.getSbbRef() != null) {
				for (org.mobicents.slee.container.component.deployment.jaxb.slee.sbb.SbbRef sr : this.sbb
						.getSbbRef()) {
					this.sbbRefs.add(new MSbbRef(sr));
				}
			}

			// Profile Refs
			this.profileSpecRefs = new ArrayList<MProfileSpecRef>();
			if (this.sbb.getProfileSpecRef() != null) {
				for (org.mobicents.slee.container.component.deployment.jaxb.slee.sbb.ProfileSpecRef psr : this.sbb
						.getProfileSpecRef()) {
					// Second arg == Alias, its depraceted in 1.1
				  MProfileSpecRef p = new MProfileSpecRef(psr);
					this.profileSpecRefs.add(p);
				}
			}

			this.sbbAbstractClass = new MSbbAbstractClass(this.sbb
					.getSbbClasses().getSbbAbstractClass());

			// Optional
			if (this.sbb.getSbbClasses().getSbbActivityContextInterface() != null) {
				this.sbbActivityContextInterface = new MSbbActivityContextInterface(
						this.sbb.getSbbClasses()
								.getSbbActivityContextInterface());
			}
			// Optional
			if (this.sbb.getSbbClasses().getSbbUsageParametersInterface() != null) {
				this.sbbUsageParametersInterface = new MUsageParametersInterface(
						this.sbb.getSbbClasses()
								.getSbbUsageParametersInterface());
			}

			// Optional
			if (this.sbb.getAddressProfileSpecAliasRef() != null) {
				this.addressProfileSpecAliasRef = this.sbb
						.getAddressProfileSpecAliasRef().getvalue();
			}

			this.events = new ArrayList<MEventEntry>();
			if (this.sbb.getEvent() != null) {
				for (org.mobicents.slee.container.component.deployment.jaxb.slee.sbb.Event e : this.sbb
						.getEvent()) {
					this.events.add(new MEventEntry(e));
				}
			}

			this.activityContextAttributeAliases = new ArrayList<MActivityContextAttributeAlias>();
			if (this.sbb.getActivityContextAttributeAlias() != null) {
				for (org.mobicents.slee.container.component.deployment.jaxb.slee.sbb.ActivityContextAttributeAlias acaa : this.sbb
						.getActivityContextAttributeAlias()) {
					this.activityContextAttributeAliases
							.add(new MActivityContextAttributeAlias(acaa));
				}
			}
			this.envEntries = new ArrayList<MEnvEntry>();
			if (this.sbb.getEnvEntry() != null) {
				for (org.mobicents.slee.container.component.deployment.jaxb.slee.sbb.EnvEntry ee : this.sbb
						.getEnvEntry()) {
					this.envEntries.add(new MEnvEntry(ee));
				}
			}

			this.resourceAdaptorTypeBindings = new ArrayList<MResourceAdaptorTypeBinding>();
			if (this.sbb.getResourceAdaptorTypeBinding() != null) {
				for (org.mobicents.slee.container.component.deployment.jaxb.slee.sbb.ResourceAdaptorTypeBinding ratb : this.sbb
						.getResourceAdaptorTypeBinding()) {
					this.resourceAdaptorTypeBindings
							.add(new MResourceAdaptorTypeBinding(ratb));
				}
			}

			this.ejbRefs = new ArrayList<MEjbRef>();
			if (this.sbb.getEjbRef() != null) {
				for (org.mobicents.slee.container.component.deployment.jaxb.slee.sbb.EjbRef er : this.sbb
						.getEjbRef()) {
					this.ejbRefs.add(new MEjbRef(er));
				}
			}
		}
		
    buildDependenciesSet();
	}
	
  private void buildDependenciesSet()
  {
    for(MSbbRef sbbRef : sbbRefs)
    {
      this.dependenciesSet.add( sbbRef.getComponentID() );
    }

    for(MProfileSpecRef profileSpecRef : profileSpecRefs)
    {
      this.dependenciesSet.add( profileSpecRef.getComponentID() );
    }

    for(MLibraryRef libraryRef : libraryRefs)
    {
      this.dependenciesSet.add( libraryRef.getComponentID() );
    }

    // FIXME: EJB's do not have component ID... what gives?
    // for(MEjbRef ejbRef : ejbRefs)
    // {
    //   this.dependenciesSet.add( ejbRef.getComponentID() );
    // }
  }

  /*
	 * (non-Javadoc)
	 * 
	 * @seeorg.mobicents.slee.container.component.deployment.jaxb.descriptors.
	 * JAXBBaseUtilityClass#getJAXBDescriptor()
	 */
	@Override
	public Object getJAXBDescriptor() {
		if (isSlee11()) {
			return this.llSbb;
		} else {
			return this.sbb;
		}
	}

	public static SbbDescriptorImpl[] parseDocument(Document sbbJar,
			DeployableUnitID duID) throws DeploymentException {
		if (isDoctypeSlee11(sbbJar.getDoctype())) {
			try {
				org.mobicents.slee.container.component.deployment.jaxb.slee11.sbb.SbbJar psj = (org.mobicents.slee.container.component.deployment.jaxb.slee11.sbb.SbbJar) JAXBBaseUtilityClass
						.getUnmarshaller(false).unmarshal(sbbJar);
				if (psj.getSbb() == null || psj.getSbb().size() == 0) {
					// Akward
					throw new ParseException(
							"No elements to parse in sbb-jar descriptor", 0);
				}
				SbbDescriptorImpl[] table = new SbbDescriptorImpl[psj.getSbb()
						.size()];
				for (int i = 0; i < psj.getSbb().size(); i++) {
					table[i] = new SbbDescriptorImpl(sbbJar, psj, i);
				}
				return table;
			} catch (Exception e) {

				throw new DeploymentException(
						"Failed to parse xml descriptor of a sbb jar due to: ",
						e);
			}

		} else {
			try {
				org.mobicents.slee.container.component.deployment.jaxb.slee.sbb.SbbJar psj = (org.mobicents.slee.container.component.deployment.jaxb.slee.sbb.SbbJar) JAXBBaseUtilityClass
						.getUnmarshaller(true).unmarshal(sbbJar);
				if (psj.getSbb() == null || psj.getSbb().size() == 0) {
					// Akward
					throw new ParseException(
							"No elements to parse in sbb-jar descriptor", 0);
				}
				SbbDescriptorImpl[] table = new SbbDescriptorImpl[psj.getSbb()
						.size()];
				for (int i = 0; i < psj.getSbb().size(); i++) {
					table[i] = new SbbDescriptorImpl(sbbJar, psj, i);
				}
				return table;
			} catch (Exception e) {

				throw new DeploymentException(
						"Failed to parse xml descriptor of a sbb jar due to: ",
						e);
			}
		}
	}

	public int getIndex() {
		return index;
	}

	public String getDescription() {
		return description;
	}

	public SbbID getSbbID() {
		return sbbID;
	}

	public String getSbbAlias() {
		return sbbAlias;
	}

	public List<MSbbRef> getSbbRefs() {
		return sbbRefs;
	}

	public List<MProfileSpecRef> getProfileSpecReference() {
		return profileSpecRefs;
	}

	public MSbbAbstractClass getSbbAbstractClass() {
		return sbbAbstractClass;
	}

	public MSbbLocalInterface getSbbLocalInterface() {
		return sbbLocalInterface;
	}

	public MSbbActivityContextInterface getSbbActivityContextInterface() {
		return sbbActivityContextInterface;
	}

	public MUsageParametersInterface getSbbUsageParametersInterface() {
		return sbbUsageParametersInterface;
	}

	public String getAddressProfileSpecAliasRef() {
		return addressProfileSpecAliasRef;
	}

	public List<MEventEntry> getEvents() {
		return events;
	}

	public List<MActivityContextAttributeAlias> getActivityContextAttributeAliases() {
		return activityContextAttributeAliases;
	}

	public List<MEnvEntry> getEnvEntries() {
		return envEntries;
	}

	public List<MResourceAdaptorTypeBinding> getResourceAdaptorTypeBindings() {
		return resourceAdaptorTypeBindings;
	}

	public Set<MLibraryRef> getLibraryRefs() {
		return libraryRefs;
	}

	public List<MEjbRef> getEjbRefs() {
		return ejbRefs;
	}

	public MSecurityPermissions getSecurityPermisions() {
		return securityPermisions;
	}

	public Set<ComponentID> getDependenciesSet() {
		return this.dependenciesSet;
	}

}
