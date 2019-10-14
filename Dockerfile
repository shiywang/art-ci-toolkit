FROM centos:7

RUN yum install -y epel-release gcc git krb5-devel python-devel python2 python3
RUN yum install -y python-pip
RUN pip install koji
RUN pip3 install tox twine setuptools wheel codecov
RUN curl -Ls https://github.com/stedolan/jq/releases/download/jq-1.6/jq-linux64 --output jq \
    && chmod +x jq && mv jq /usr/local/bin/jq

RUN pip install rh-doozer==0.5.17 rh-elliott==0.2.11 rh-ocp-build-data-validator==0.0.8

RUN useradd -ms /bin/bash -u 1000 art
USER art

CMD ["tox"]
