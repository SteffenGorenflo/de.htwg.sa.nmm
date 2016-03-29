package de.htwg.sa.nmm.persistence;

import de.htwg.sa.nmm.persistence.db4o.db4oDao;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public enum PersistenceStrategy {

    db4o {
        public IDAO createDao() {
            return new db4oDao();
        }
    },

    hibernate {
        public IDAO createDao() {
            throw new NotImplementedException();
        }
    },

    couchdb {
        public IDAO createDao() {
            throw new NotImplementedException();
        }
    };

    public IDAO createDao() {
        return null;
    }
}
