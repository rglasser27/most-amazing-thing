package com.apollowebworks.mostamazingthing.ui;

import com.apollowebworks.mostamazingthing.scene.Scene;

interface Animation {
	Scene getScene();
	void draw();
	boolean tick();
}
