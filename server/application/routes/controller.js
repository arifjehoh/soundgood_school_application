const db = require('../models');
const model = db.model;

// GET Instruments that are not rented.
exports.getInstruments = (req, res) => {
    model.findAll().
        then(instruments => res.json(instruments));
};
// PUT Rent a instrument.
// PUT Terminate a rented instrument.
