include config.mk

export JAVA_HOME

start:
	$(WILDFLY_DIR)/bin/standalone.sh &

stop:
	$(WILDFLY_DIR)/bin/jboss-cli.sh --connect command=:shutdown

mvn:
	mvn $(filter-out $@,$(MAKECMDGOALS))

log:
	tail -f /opt/wildfly-28.0.1.Final/standalone/log/server.log