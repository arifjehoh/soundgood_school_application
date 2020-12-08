const config = require ('../config/db.config.js');
const Sequelize = require ('sequelize');

const sequelize = new Sequelize (config.DATABASE, config.USER, config.PASSWORD, {
    host: 'mysql',
    dialect: config.DIALECT,
});

const db = {};

db.sequelize = Sequelize;
db.sequelize = sequelize;

db.model = require ('./model.js')(sequelize, Sequelize);

module.exports = db;
