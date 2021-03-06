FROM centos:8

RUN yum install -y epel-release @python36
RUN yum install -y \
  gcc \
  gcc-c++ \
  git \
  jq \
  krb5-devel \
  libcurl-devel \
  libgit2 \
  openssl-devel \
  rpm-devel \
  python3-{devel,pip,pygit2}

# Those environment variables are required to install pycurl, koji, and rpkg with pip
ENV PYCURL_SSL_LIBRARY=openssl RPM_PY_SYS=true

RUN pip3 install koji tox twine setuptools wheel codecov future \
  rh-doozer==1.2.27 rh-elliott==1.0.15 rh-ocp-build-data-validator==0.1.22

RUN useradd -ms /bin/bash -u 1000 art
USER art

CMD ["tox"]
