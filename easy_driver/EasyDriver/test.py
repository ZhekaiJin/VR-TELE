#!/usr/bin/python
# -*- coding: utf-8 -*-
import fileinput
import sys
#pubnub import
from pubnub.callbacks import SubscribeCallback
from pubnub.enums import PNStatusCategory
from pubnub.pnconfiguration import PNConfiguration
from pubnub.pubnub import PubNub

import simplejson as sj

#easy driver import
import easydriver as ed

#pubnub setup
pnconfig = PNConfiguration()
 
pnconfig.subscribe_key = 'sub-c-e1ee380e-d3b8-11e7-b83f-86d028961179'
pnconfig.publish_key = 'pub-c-012ea47d-f770-4c04-8176-1cd817b319b4' #dont need publish rn
 
pubnub = PubNub(pnconfig)

#stepper driver setup
cw = True
ccw = False

stepper_pan = ed.easydriver(18, 0.1, 23, 24, 17, 25)
stepper_tilt= ed.easydriver(12, 0.1, 16, 24, 17, 25)#pin numbers need to be figured out


#global const, prob a good idea to publish those



#class setup and call back func 
class MySubscribeCallback(SubscribeCallback):
	degree_pan = 0
	degree_tilt= 0
	pan_tg = 0
	tilt_tg = 0
	
	

	def presence(self, pubnub, presence):
		pass  # handle incoming presence data
 
	def status(self, pubnub, status):
		if status.category == PNStatusCategory.PNUnexpectedDisconnectCategory:
			pass  # This event happens when radio / connectivity is lost
 
        #elif status.category == PNStatusCategory.PNConnectedCategory:
            # Connect event. You can do stuff like publish, and know you'll get it.
            # Or just use the connected event to confirm you are subscribed for
            # UI / internal notifications, etc
            #pubnub.publish().channel("scott").message("hello!!").async(my_publish_callback)
		elif status.category == PNStatusCategory.PNReconnectedCategory:
			pass
            # Happens as part of our regular operation. This event happens when
            # radio / connectivity is lost, then regained.
		elif status.category == PNStatusCategory.PNDecryptionErrorCategory:
			pass
            # Handle message decryption error. Probably client configured to
            # encrypt messages and on live data feed it received plain text.
 
	def message(self, pubnub, message):

		try:
			if not (type(message.message) is dict):
				return
			msg=message.message
			self.pan_tg = msg.get("right_hand").get("right_yaw")
			self.tilt_tg = msg.get("right_hand").get("right_pitch")
			self.move_motor()

		except KeyboardInterrupt:
			    sys.exit(0)
		except: 
			pass
	def move_motor(self):
		
		if self.degree_pan < self.pan_tg:
			stepper_pan.set_direction(cw)
			stepper_pan.set_full_step()
			stepper_pan.step()
			self.degree_pan+=1

		elif self.degree_pan > self.pan_tg:
			stepper_pan.set_direction(ccw)
			stepper_pan.set_full_step()
			stepper_pan.step()
			self.degree_pan-=1
		else:
			pass
	
		if self.degree_tilt < self.tilt_tg:
			stepper_tilt.set_direction(cw)
			stepper_tilt.set_full_step()
			stepper_tilt.step()
			self.degree_tilt+=1
		elif self.degree_tilt > self.tilt_tg:
			stepper_tilt.set_direction(ccw)
			stepper_tilt.set_full_step()
			stepper_tilt.step()
			self.degree_tilt-=1
		else:
			pass
	
		
		print(self.degree_pan,"degree_pan")
		print(self.degree_tilt,"degree_tilt")
		print(self.pan_tg,"pan_tg")
		print(self.tilt_tg,"tilt_tg")



	
         # Handle new message stored in message.message
 
 
pubnub.add_listener(MySubscribeCallback())
pubnub.subscribe().channels('scott').execute()

