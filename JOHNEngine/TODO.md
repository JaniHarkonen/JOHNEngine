## TODO

- handle scrolling in Input
???? get information about the monitor from window
??? should ABasicWindowRequests be implemented using lambdas instead???
- remove cringe
???? new exception class??
- ABufferedRequestManager should have a template
- MAJOR: instead of spawning threads each time there are new load requests,
AssetRequestManager should spawn the threads once and then distribute the 
work in real time

### Renderer structure

=== setting up the shader program
- create shader program
- declare uniforms
- create shaders
	-- create x
	-- set source x
	-- compile x
	-- attach to shader program x
- link shader program
- deload shaders
	-- detach
	-- delete

=== rendering
- bind shader program

=== disposing
- unbind shader program
- delete shader program