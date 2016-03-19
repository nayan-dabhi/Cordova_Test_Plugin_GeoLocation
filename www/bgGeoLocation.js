/*global cordova, module*/

module.exports = {
  start: function (data, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "BgGeoLocation", "start", [data]);
  }
};
