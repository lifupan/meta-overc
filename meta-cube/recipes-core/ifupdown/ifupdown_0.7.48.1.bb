SUMMARY = "ifupdown: basic ifup and ifdown used by initscripts"
DESCRIPTION = "High level tools to configure network interfaces \
This package provides the tools ifup and ifdown which may be used to \
configure (or, respectively, deconfigure) network interfaces, based on \
the file /etc/network/interfaces."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

SRC_URI = "http://archive.ubuntu.com/ubuntu/pool/main/i/ifupdown/ifupdown_0.7.48.1ubuntu5.tar.gz \
	   file://defn2-c-man-don-t-rely-on-dpkg-architecture-to-set-a.patch \
	   file://inet-6-.defn-fix-inverted-checks-for-loopback.patch \
	   file://99_network \
	  "

EXTRA_OEMAKE = ""

# needed so we don't get default S="${WORKDIR}/ifupdown-${PV}"
S = "${WORKDIR}/ifupdown-${PV}ubuntu5"


inherit update-rc.d systemd

do_compile () {
	chmod a+rx *.pl *.sh
	oe_runmake 'CC=${CC}' "CFLAGS=${CFLAGS} -Wall -W -D'IFUPDOWN_VERSION=\"${PV}\"'"
}

do_install () {
	install -d ${D}${mandir}/man8 \
		  ${D}${mandir}/man5 \
		  ${D}${base_sbindir}

	# If volatiles are used, then we'll also need /run/network there too.
	install -d ${D}/etc/default/volatiles
	install -m 0644 ${WORKDIR}/99_network ${D}/etc/default/volatiles

	install -m 0755 ifup ${D}${base_sbindir}/
	ln ${D}${base_sbindir}/ifup ${D}${base_sbindir}/ifdown
	install -m 0644 ifup.8 ${D}${mandir}/man8
	install -m 0644 interfaces.5 ${D}${mandir}/man5
	cd ${D}${mandir}/man8 && ln -s ifup.8 ifdown.8
}

ALTERNATIVE_PRIORITY = "100"
ALTERNATIVE_${PN} = "ifup ifdown"

ALTERNATIVE_LINK_NAME[ifup] = "${base_sbindir}/ifup"
ALTERNATIVE_LINK_NAME[ifdown] = "${base_sbindir}/ifdown"

INITSCRIPT_NAME = "ifup"
INITSCRIPT_PARAMS = "start 39 S . stop 39 0 6 1 ."

SRC_URI[md5sum] = "85ba375f3c6f26d34efb2a8575e77fc8"
SRC_URI[sha256sum] = "08dce14692c07b72b583b86c4d3ace0d9dac1928925144cc3ddde15b694ebbdf"
