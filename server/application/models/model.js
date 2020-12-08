module.exports = (sequelize, Sequelize) => {
    const instrument = sequelize.define('instruments', {
        instrument_id: { type: Sequelize.INTEGER },
        rental_id: { type: Sequelize.INTEGER },
        student_id: { type: Sequelize.INTEGER },
        instrument: { type: Sequelize.STRING },
        instrument_cost: { type: Sequelize.INTEGER },
    });
    return instrument;
};
