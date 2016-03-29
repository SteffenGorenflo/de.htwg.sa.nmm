package de.htwg.sa.nmm.persistence;

import de.htwg.sa.nmm.persistence.db4o.db4oDao;

public enum PersistenceStrategy {

    db4o {
        public IDAO createDao() {
            return new db4oDao();
        }
    },

    hibernate {
        public IDAO createDao() {
            return null;
        }
    },

    couchdb {
        public IDAO createDao() {
            return null;
        }
    };

    public IDAO createDao() {
        return null;
    }
}
