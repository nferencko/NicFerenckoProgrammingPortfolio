# FROM mcr.microsoft.com/devcontainers/cpp:0-debian-11
FROM mcr.microsoft.com/devcontainers/cpp:debian

ARG REINSTALL_CMAKE_VERSION_FROM_SOURCE="none"

# keep CXX here so google test works via the command line
ENV CXX='/usr/bin/clang++-16 -fstandalone-debug -g'

# Optionally install the cmake for vcpkg
COPY ./reinstall-cmake.sh /tmp/

RUN if [ "${REINSTALL_CMAKE_VERSION_FROM_SOURCE}" != "none" ]; then \
        chmod +x /tmp/reinstall-cmake.sh && /tmp/reinstall-cmake.sh ${REINSTALL_CMAKE_VERSION_FROM_SOURCE}; \
    fi \
    && rm -f /tmp/reinstall-cmake.sh

# [Optional] Uncomment this section to install additional vcpkg ports.
# RUN su vscode -c "${VCPKG_ROOT}/vcpkg install <your-port-name-here>"

# [Optional] Uncomment this section to install additional packages.
# RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#     && apt-get -y install --no-install-recommends enscript ghostscript poppler-utils

# RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#     && apt-get -y install --no-install-recommends enscript ghostscript poppler-utils clang-16

# [Optional] Uncomment this section to install additional packages.
RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends enscript ghostscript \
		poppler-utils acl clang-16
    # ltrace

# only uncomment one below:
#Mac M1
# RUN wget http://http.us.debian.org/debian/pool/main/v/valgrind/valgrind_3.20.0-2.1_arm64.deb && sudo apt-get -y install ./valgrind_3.20.0-2.1_arm64.deb 

#Intel/AMD
RUN wget https://zeus.cs.pacificu.edu/software/valgrind_3.20.0-2.1_amd64.deb && \
 sudo apt-get -y install ./valgrind_3.20.0-2.1_amd64.deb && \
 rm -rf ./valgrind_3.20.0-2.1_amd64.deb
