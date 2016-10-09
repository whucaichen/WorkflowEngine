/**
 * Created by Chance on 16/09/22.
 */
//load("nashorn:mozilla_compat.js");
//importPackage(java.util);

var list2 = java.util.Arrays.asList(['A', 'B', 'C']);
function callJava() {
    var s = com.chance.JSEngine.hello();
    list2[2] = s;
    return list2;
}

function sendTcp(msg) {
    return com.chance.SocketHelper.sendTcp(msg);
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

//var choice;
function svBuzMenu() {
    print("$ svBuzMenu");
    var choice = sendTcp("Menu Select");
    print("$ item selected：" + choice);
    return choice;
}
//function svBuzMenu(choice) {
//    print("$ svBuzMenu-" + choice);
//    svDoSomething();
//    return choice;
//}

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

function svDoSomething() {
    var i = 0;
    while (i < 500000000) i++;//模拟延迟
    //setTimeout(function () {//错误，非js原生方法
    var r = (Math.random() + "").charAt(3);
    if (r == "0") {
        print("$ sv error");
    } else {
        print("$ sv done");
    }
    //}, 1000);
    return r;
}

//var data = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
//var filtered = data.filter(function (i) {
//    return i % 2 == 0;
//});
//print(filtered);
//var sumOfFiltered = filtered.reduce(function (acc, next) {
//    return acc + next;
//}, 0);
//print(sumOfFiltered);