module.exports = app => {
    const controller = require('./controller.js');
    var router = require('express').Router();

    /**
     * ROUTES
     **/

    // TODO all not rented instrument for specified instrument.
    router.get('/instruments', controller.getInstruments);
    // TODO rent a instrument, student can't rent more then two instruments.
    // router.put('/rental', controller.rentInstrument);
    // TODO Terminate a rented instrument.
    // router.put('/rental[id]', controller terminateRental);
};

