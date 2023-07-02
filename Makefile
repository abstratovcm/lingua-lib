include config.mk

start:
	$(WILDFLY_DIR)/bin/standalone.sh &

stop:
	$(WILDFLY_DIR)/bin/jboss-cli.sh --connect command=:shutdown

