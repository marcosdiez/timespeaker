TimeSpeaker
===========

WARNING: This app uses the Device Administrator permission. More info below, after the main description.


This app speaks the time every time you get your lock screen password wrong on Android.

This software actually improves my sleep. If, while sleeping (or almost waking up)
I open my eyes, than I can't get back to sleep. So I learned how to wake up without
opening my eyes. Apparently this is not hard, as I know other people who can do
it as well.

So I wake up and, with my eyes closed, grab my phone (which is of course next to my bed).
Afterwards I turn the screen on, which is easy because it is a hardware button.
Then I just hover my finger randomly at the screen, which should now be the lock screen.

Of course my password will be mistyped and that is when this software will just speak
out loud what time it is.

This way I know if I must wake up or if I can sleep for some more minutes without
having to open my eyes !

----------
Technical Notes:

For this software to work you must set it as a device administrator.
The only thing it does as a device administrator is receive a signal from Android telling
that the password was not swiped correctly. Whenever it receives this signal, it speaks the time.

For you to uninstall this app, you must manually remove the device administrator permission.
That is an Android idiosyncrasy in which I have nothing to do with.
Go to Settings / Security / Device Administrators to set or unset it.

Also, on Android, if the unlock pattern is too small (less than 3 dots), Android won't
send a signal to my app.


----------

Sweet Dreams !

This is Free Software: https://github.com/marcosdiez/timespeaker

By marcos AT unitron DOT com DOT br
2017-08-14