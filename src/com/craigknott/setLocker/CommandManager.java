package com.craigknott.setLocker;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class CommandManager implements CommandExecutor {

	private SetLocker plugin;
	private LockManager lockManager;

	public CommandManager(SetLocker plugin) {
		this.plugin = plugin;
		lockManager = new LockManager();
	}

	public void sendError(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.valueOf("RED").toString().concat(message));
	}

	public boolean lock(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sendError(sender, "This command may only be invoked by a player");
			return true;
		}

		if (args.length == 0) {
			sendError(sender, "Too few arguments");
			return true;
		}

		switch (args[0]) {
		case ("createRegion"):
			if ( ((Player)sender).hasPermission("setManager") ) {
				if (args.length == 1) {
					sendError(sender, "Missing region argument (/lock createRegion <name>)");
				} else {
					createRegion(sender, args[1]);
				}
			} else {
				sendError(sender, "You do not have permission to do this");
			}
			break;
		case ("deleteRegion"):
			if (args.length == 1) {
				sendError(sender, "Missing region argument (/lock deleteRegion <region>)");
			} else {
				deleteRegion(sender, args[1]);
			}
			break;
		case ("acquire"):
			if (args.length == 1) {
				sendError(sender, "Missing region argument (/lock acquire <region>)");
			} else {
				acquireLock(sender, args[1]);
			}
			break;
		case ("release"):
			if (args.length == 1) {
				sendError(sender, "Missing region argument (/lock release <region>)");
			} else {
				releaseLock(sender, args[1]);
			}
			break;
		case ("regionInfo"):
			if (args.length == 1) {
				sendError(sender,
						"Missing region argument (/lock regionInfo <region>)");
			} else {
				regionInfo(sender, args[1]);
			}
			break;
		case ("addPlayer"):
			if (args.length == 1) {
				sendError(sender,
						"Missing region argument (/lock addPlayer <region> <username>)");
			} else if (args.length == 2) {
				sendError(sender,
						"Missing username argument (/lock addPlayer <region> <username>)");
			} else {
				addPlayer(sender, args[1], args[2]);
			}
			break;
		case ("removePlayer"):
			if (args.length == 1) {
				sendError(sender,
						"Missing region argument (/lock removePlayer <region> <username>)");
			} else if (args.length == 2) {
				sendError(sender,
						"Missing username argument (/lock removePlayer <region> <username>)");
			} else {
				removePlayer(sender, args[1], args[2]);
			}
			break;
		case ("list"):
			lockList(sender);
			break;
		case ("leave"):
			if (args.length == 1) {
				sendError(sender,
						"Missing region argument (/lock leave <region>)");
			} else {
				leaveRegion(sender, args[1]);
			}
			break;
		case ("swapOwner"):
			if (args.length == 1) {
				sendError(sender,
						"Missing region argument (/lock swapOwner <region> <new-owner>)");
			} else if (args.length == 2) {
				sendError(sender,
						"Missing new-owner argument (/lock swapOwner <region> <new-owner>)");
			} else {
				swapOwner(sender, args[1], args[2]);
			}
			break;
		case ("about"):
			displayAbout(sender);
			break;
		default:
			sendError(
					sender,
					"The first argument was invalid, please specify either: createRegion, deleteRegion, addPlayer, removePlayer, leave, regionInfo, swapOwner, acquire, release, list, warpTo or about");
			break;
		}

		return true;
	}

	public void displayAbout(CommandSender sender) {
		String message = "SetLocker was produced by Craig Knott (Nargarawr). \n"
				+ "If you encounter any bugs, please email me at psyck@nottingham.ac.uk.\n"
				+ "The source code is available at https://github.com/nargarawr/SetLocker\n"
				+ "Documentation available at http://tinyurl.com/SetLockerPdf";
		sender.sendMessage(ChatColor.valueOf("GOLD").toString().concat(message));
	}

	public boolean swapOwner(CommandSender sender, String region,
			String newOwner) {
		Lock l = lockManager.getLockByName(region);

		if (l != null) {
			if (l.isLocked()
					&& (l.getWarden().equalsIgnoreCase(((Player) sender)
							.getName()))) {
				l.swapOwner(newOwner);
			} else if (l.isLocked()
					&& (!(l.getWarden().equalsIgnoreCase(((Player) sender)
							.getName())))) {
				sendError(sender, "You are not the owner of this region");
			} else {
				sendError(sender, "You are not the owner of this region");
			}
		} else {
			sendError(sender, "That region does not exist");
		}
		return true;
	}

	public boolean regionInfo(CommandSender sender, String name) {
		Lock l = lockManager.getLockByName(name);

		if (l != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("\nRegion: " + name + "\n");
			sb.append("Locked?: " + l.isLocked() + "\n");
			sb.append("Location:\n");
			sb.append("(" + l.getRegion().getMax_point().getX() + ", ");
			sb.append(l.getRegion().getMax_point().getY() + ", ");
			sb.append(l.getRegion().getMax_point().getZ() + ")\n");
			sb.append("(" + l.getRegion().getMin_point().getX() + ", ");
			sb.append(l.getRegion().getMin_point().getY() + ", ");
			sb.append(l.getRegion().getMin_point().getZ() + ")\n");
			if (l.isLocked()) {
				sb.append("Owner: " + l.getWarden() + "\n");

				if (l.getCellMateCount() > 0) {
					sb.append("Crew: ");
				}

				for (String s : l.getCellMates()) {
					if (!(s.equalsIgnoreCase(l.getWarden()))) {
						sb.append(s + ", ");
					}
				}

			}
			sender.sendMessage(sb.toString());

		} else {
			sendError(sender, "That region does not exist");
		}
		return true;

	}

	public boolean addPlayer(CommandSender sender, String name, String player) {
		Lock l = lockManager.getLockByName(name);

		if (l != null) {
			if (l.isLocked()
					&& (l.getWarden().equalsIgnoreCase(((Player) sender)
							.getName()))) {
				sender.sendMessage(l.addCellMates(player));
			} else if (l.isLocked()
					&& (!(l.getWarden().equalsIgnoreCase(((Player) sender)
							.getName())))) {
				sendError(sender, "You are not the owner of this region");
			} else {
				sendError(sender, "You are not the owner of this region");
			}
		} else {
			sendError(sender, "That region does not exist");
		}

		return true;
	}

	public boolean removePlayer(CommandSender sender, String name, String player) {
		Lock l = lockManager.getLockByName(name);

		boolean notFound = true;

		if (l != null) {
			if (l.isLocked()
					&& (l.getWarden().equalsIgnoreCase(((Player) sender)
							.getName()))) {
				for (String s : l.getCellMates()) {
					if (s.equalsIgnoreCase(player)) {
						l.removeCellMate(player);
						if (player.equalsIgnoreCase(l.getWarden())) {
							l.releaseLock();
							sender.sendMessage("Sucessfully removed, and lock released");
						} else {
							sender.sendMessage("Sucessfully removed");
						}
						return true;
					}
				}
				if (notFound) {
					sendError(sender,
							"That player does not belong to this region");
				}
			} else if (l.isLocked()
					&& (!(l.getWarden().equalsIgnoreCase(((Player) sender)
							.getName())))) {
				sendError(sender, "You are not the owner of this region");
			} else {
				sendError(sender, "You are not the owner of this region");
			}
		} else {
			sendError(sender, "That region does not exist");
		}
		return true;
	}

	public boolean leaveRegion(CommandSender sender, String name) {
		Lock l = lockManager.getLockByName(name);

		boolean notFound = true;

		if (l != null) {
			for (String s : l.getCellMates()) {
				if (s.equalsIgnoreCase(((Player) sender).getName())) {
					l.removeCellMate(((Player) sender).getName());
					if (((Player) sender).getName().equalsIgnoreCase(
							l.getWarden())) {
						l.releaseLock();
						sender.sendMessage("Sucessfully left, lock released");
					} else {
						sender.sendMessage("Sucessfully left");
					}
					return true;
				}
			}
			if (notFound) {
				sendError(sender, "You do not belong to this region");
			}

		} else {
			sendError(sender, "That region does not exist");
		}
		return true;
	}

	public synchronized boolean acquireLock(CommandSender sender, String name) {
		Lock l = lockManager.getLockByName(name);

		if (l != null) {
			l.acquireLock(((Player) sender).getName());
			sender.sendMessage("Sucessfully Locked");
		} else {
			sendError(sender,
					"This region is already locked and cannot be acquired");
		}
		return true;
	}

	public synchronized boolean releaseLock(CommandSender sender, String name) {
		Lock l = lockManager.getLockByName(name);

		if (l != null) {
			if ((l.getWarden().equalsIgnoreCase(((Player) sender).getName()))) {
				sender.sendMessage(l.releaseLock());
			} else {
				sendError(sender,
						"You are not the holder of this lock, and cannot release it");
			}
		} else {
			sendError(sender, "No such region exists");
		}

		return true;
	}

	public boolean unique(String name) {
		for (Lock l : lockManager.getLocks()) {
			if (l.getRegion().getName().equalsIgnoreCase(name)) {
				return false;
			}
		}

		return true;
	}

	public boolean deleteRegion(CommandSender sender, String name) {
		Lock l = lockManager.getLockByName(name);
		if (l != null) {
			sender.sendMessage(lockManager.delete(l));
		} else {
			sendError(sender, "Region does not exist");
		}
		return true;
	}

	public boolean createRegion(CommandSender sender, String name) {
		WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer()
				.getPluginManager().getPlugin("WorldEdit");
		Selection selection = worldEdit.getSelection((Player) sender);

		if (selection != null) {
			if (unique(name.toString())) {
				Location max_point = selection.getMaximumPoint();
				Location min_point = selection.getMinimumPoint();
				if ( checkBreaches(max_point) == null && checkBreaches(min_point) == null){
					RegionNamePair r = new RegionNamePair(name.toString(),
							min_point, max_point);
					Lock l = new Lock(r);
					lockManager.addLock(l);

					sender.sendMessage("Added sucessfully");	
				} else {
					sendError(sender, "Regions may not overlap (+/- 1)");
				}
			} else {
				sendError(sender, "That region name has been used already");
			}
		} else {
			sendError(sender, "No selection has been made");
		}
		return true;
	}

	public boolean lockList(CommandSender sender) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n=================\n");
		sb.append(" Current Regions\n");
		sb.append("=================\n");

		for (Lock l : lockManager.getLocks()) {
			String locked = null;
			if (l.isLocked()) {
				locked = "(Locked by " + l.getWarden() + ")\n";
			} else {
				locked = "(Free)\n";
			}
			sb.append(l.getRegion().getName() + " " + locked);
		}
		sender.sendMessage(sb.toString());
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("lock")) {
			return lock(sender, label, args);
		}
		return false;
	}

	public Lock checkBreaches(Location location) {
		for (Lock l : lockManager.getLocks()) {
			Location min = l.getRegion().getMin_point();
			Location max = l.getRegion().getMax_point();
			if (isInRegion(location, min, max)) {
				return l;
			}
		}
		return null;
	}

	public boolean isInRegion(Location foreignObject, Location region_min,
			Location region_max) {
		return ((foreignObject.getX() <= region_max.getX() + 1)
				&& (foreignObject.getX() >= region_min.getX() - 1)
				&& (foreignObject.getZ() <= region_max.getZ() + 1)
				&& (foreignObject.getZ() >= region_min.getZ() - 1)
				&& (foreignObject.getY() <= region_max.getY() + 1) 
				&& (foreignObject.getY() >= region_min.getY() - 1));
	}

	public void releaseOwnerships(String name) {
		for (Lock l : lockManager.getLocks()) {
			if (l.isLocked() && l.getWarden().equalsIgnoreCase(name)) {
				if (l.getCellMateCount() == 0) {
					l.releaseLock();
				} else {
					String newOwner = name;
					while (newOwner.equalsIgnoreCase(name)) {
						Random r = new Random();
						int x = r.nextInt() % l.getCellMateCount() + 1;
						newOwner = l.getCellMateByIndex(x);
					}
					l.swapOwner(newOwner);
				}
			}

		}
	}

}
