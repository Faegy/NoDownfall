package io.github.faegy.nodownfall;

import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.world.ChangeWorldWeatherEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.world.weather.Weathers;

import com.google.inject.Inject;

@Plugin(id = "no-downfall", name = "No Downfall", version = "1.1", authors= "Faegy", description="This plugin will prevent all downfall on the entire server. Use '/weather CLEAR' and the plugin will block any later change of state.")
public class plugin {
	private Logger logger;

	@Inject
	private void setLogger(Logger logger) {
	    this.logger = logger;
	}
	
	public Logger getLogger() {
	    return logger;
	}
	
	@Listener
    public void onServerStart(GameStartedServerEvent event) {
		getLogger().info("No Downfall enabled, sun will be shining!");
    }
	
	@Listener
	public void onWeatherChange(ChangeWorldWeatherEvent event) {
		if (event.getOriginalWeather() != Weathers.CLEAR) {
			getLogger().info("Detected an attempt of weather change!");
			event.setWeather(Weathers.CLEAR);
			event.setDuration(Integer.MAX_VALUE);
			if (event.getWeather() != Weathers.CLEAR) {
				getLogger().warn("Couldn't enforce sunshine! Are you using another plugin to enforce rain?");
			}
			if (event.getWeather() == Weathers.CLEAR) {
				getLogger().info("We managed to enforce sunshine!");
			}
		}
	}
	
	@Listener
    public void onServerStop(GameStoppedServerEvent event) {
		getLogger().info("No Downfall was brought to you by Faegy. More info on 'https://faegy.github.io/NoDownfall'.");
    }
	
}