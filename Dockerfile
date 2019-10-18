#
# Copyright © 2017 Sven Ruppert (sven.ruppert@gmail.com)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

FROM svenruppert/adopt:1.8.212-04
MAINTAINER sven.ruppert@gmail.com
ARG USER_HOME_DIR="/root"
COPY 03_demo/target/vaadin-app.jar .
EXPOSE 8899
CMD ["java", "-jar", "vaadin-app.jar"]