from pubnub.callbacks import SubscribeCallback
from pubnub.enums import PNStatusCategory
from pubnub.pnconfiguration import PNConfiguration
from pubnub.pubnub import PubNub

pnconfig = PNConfiguration()
pnconfig.publish_key = 'pub-c-fa2f3f29-45b2-4583-ad86-9d3eb5bff779' #dont need publish rn
pnconfig.subscribe_key = 'sub-c-b861f144-d3ed-11e7-91cc-2ef9da9e0d0e' #dont need publish rn

pubnub = PubNub(pnconfig)
def publish_callback(result, status):
    pass
    # Handle PNPublishResult and PNStatus
 
pubnub.publish().channel('awesomeChannel').message({"pan" : 110 , "tilt": 14}).async(publish_callback)