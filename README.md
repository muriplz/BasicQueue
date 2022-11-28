<p align="center"><img src="https://user-images.githubusercontent.com/46825658/164082042-940d18c0-4a4f-4dd2-bdf7-bf3cdea93663.png" alt="Logo" width="200"></p>
<h1 align="center">Steam n' Rails <br>
	<a href="https://discord.gg/WZ6TjrqpXD"><img src="https://img.shields.io/discord/929394649884405761?color=5865f2&label=Discord&style=flat" alt="Discord"></a>

# Plugin Description:
When the server is full (not having in mind reserved slots), you will join the queue when trying to log in. You will get a position and you must reconnect within some amount of minutes to keep your position. If the available slots are higher or equals to your queue position, you will join the server the next time you try to log in.
All messages that are shown are always on the kick screen (instead of the typical "server is full!").
	
# Permissions
- **queue.priority** -> Gives a player priority when joining the queue
- **queue.saveleavingslot** -> When that player leaves the server, joins with priority to the queue (even if the player does not have priority permisison)
- **queue.reservedslot** -> Gives a player permission to join on a reserved slot
	
# Languages
For now there are only 2 languages: English (default) and Spanish. You can always translate the file and change the locale config option to the name of your choosing.
All PR's regarding language are more than welcome!
	
# Config.yml
```yaml
###############################################################################
#                             -<( BasicQueue )>-                              #
#                               Made by muriplz                               #
#                                                                             #
###############################################################################
#                                                                             #
# Github - https://github.com/muriplz/BasicQueue                              #
# Spigot - https://www.spigotmc.org/resources/basicqueue.101072/              #
###############################################################################

# Select language. Available are downloaded upon plugin load
# If you want to translate your own, you can create a new file and set here the name of that new file
locale: en_en

# Set the time in minutes after which a player is removed from the queue if he/she does not reconnect
queue-cooldown: 3

# Set the amount of reserved slots for the queue. 
# Having queue.reservedslot permissions lets you use slots that can't be used by default
reserved-slots: 0
	
# If on true, players leaving with queue.saveleavingslot permission will join the queue with priority
leaving-slot-priority: true
```
	
#### Special thanks to Configuration Master plugin (https://github.com/Errored-Innovations/ConfigurationMaster) that takes care of all .yml files formatting! <3
