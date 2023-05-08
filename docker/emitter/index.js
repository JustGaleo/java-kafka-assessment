const {Kafka} = require('kafkajs');
const {faker} = require('@faker-js/faker');

const kafka = new Kafka({
    clientId: 'emitter-app',
    brokers: ['kafka:9092']
});

const topicNameV1 = 'scan_topic_v1';
const topicNameV2 = 'scan_topic_v2';


const produceEvent = async (eventType, scanData, topicName) => {
    const producer = kafka.producer();
    await producer.connect();

    const bookEvent = {
        eventType,
        ...scanData
    };

    await producer.send({
        topic: topicName,
        messages: [
            {
                key: 'data',
                value: scanData // JSON.stringify(bookEvent)
            }
        ]
    });

    console.log(`Mensaje enviado al tema ${topicName}:`, bookEvent);
    await producer.disconnect();
};

const generateRandomScan = () => {
    const shelfLocations = [...Array(10).keys()].map(i => `A${i + 1}-3B`);
    const robotIds = [...Array(5).keys()].map(i => `R-${i + 1}23`);
    const productIds = [...Array(20).keys()].map(i => `P-${i + 1}67`);

    return {
        product_id: faker.helpers.arrayElement(productIds),
        timestamp: new Date().toISOString(),
        type: 'scan',
        robot_id: faker.helpers.arrayElement(robotIds),
        barcode: faker.datatype.number({min: 1000000000000, max: 9999999999999}),
        shelf_location: faker.helpers.arrayElement(shelfLocations)
    };
};

const sendRandomEventV1 = () => {
    const eventType = 'scan';
    const scan = generateRandomScan();
    produceEvent(eventType, JSON.stringify(scan), topicNameV1);
};
const sendRandomEventV2 = () => {
    const eventType = 'scan';
    const scan = generateRandomScan();

    const serializedValue = Object.entries(scan)
        .map(([key, value]) => `${key}: ${value}`)
        .join(' | ');

    produceEvent(eventType, serializedValue, topicNameV2);
}

const getRandomInterval = (max) => {
    return Math.floor(Math.random() * 1000 * max) + 1000;
};

const startSendingEvents = (maxAvailableFrecTime, sendRandomEvent) => {
    sendRandomEvent();
    setInterval(() => {
        sendRandomEvent();
    }, getRandomInterval(maxAvailableFrecTime));
};

startSendingEvents(5, sendRandomEventV1);
startSendingEvents(20, sendRandomEventV2);
