function sendTcp(msg) {
    return com.chance.SocketHelper.send(msg);
}

function svInitAll() {
    print("$ svInitAll");
    svDoSomething();
    return "ok";
}

function svInitDev1() {
    print("$ svInitDev1");
    svDoSomething();
    return "ok";
}

function svInitDev2() {
    print("$ svInitDev2");
    svDoSomething();
    return "ok";
}

function svDevSelfTest() {
    print("$ svDevSelfTest");
    svDoSomething();
    return "ok";
}

function svWaitCardAndRead(account) {
    print("$ svWaitCardAndRead-" + account);
    svDoSomething();
    return "ok";
}

function svBuzMenu() {
    print("$ svBuzMenu");
    var choice = sendTcp("Menu Select");
    print("$ item selected£º" + choice);
    return choice;
}

function svEjectAndCapCard(account) {
    print("$ svEjectAndCapCard-" + account);
    svDoSomething();
    return "ok";
}

function svWithdralInit() {
    print("$ svWithdralInit");
    svDoSomething();
    return "ok";
}

function svWithdralAmtCheck(amount) {
    print("$ svWithdralAmtCheck-" + amount);
    svDoSomething();
    return "ok";
}

function svMixDispense(amount) {
    print("$ svMixDispense-" + amount);
    svDoSomething();
    return "ok";
}

function svTrsSdAndRev(xmlData, flag, transType) {
    print("$ svTrsSdAndRev-" + xmlData + flag + transType);
    svDoSomething();
    return "done";
}

function svPoolPinPad() {
    print("$ svPoolPinPad");
    svDoSomething();
    return "ok";
}

function svEndAll() {
    print("$ svEndAll");
    svDoSomething();
    return "ok";
}

function svEndDev1() {
    print("$ svEndDev1");
    svDoSomething();
    return "ok";
}

function svEndDev2() {
    print("$ svEndDev2");
    svDoSomething();
    return "ok";
}