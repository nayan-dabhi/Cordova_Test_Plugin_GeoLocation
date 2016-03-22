/*global cordova, module*/

module.exports = {
  start_update: function (data, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "BgGeoLocation", "start_update", [data]);
  },
  stop_update: function (data, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "BgGeoLocation", "stop_update", [{}]);
  }
};
