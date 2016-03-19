/*global cordova, module*/

module.exports = {
  greet: function (data, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "BgGeoLocation", "greet", [data]);
  }
};
