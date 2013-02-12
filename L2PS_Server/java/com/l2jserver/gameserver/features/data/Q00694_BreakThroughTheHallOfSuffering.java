/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jserver.gameserver.features.data;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00694_BreakThroughTheHallOfSuffering extends Quest
{
	private static final int TEPIOS = 32603;
	
	public Q00694_BreakThroughTheHallOfSuffering(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(TEPIOS);
		addTalkId(TEPIOS);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		QuestState qs = talker.getQuestState(getName());
		if (qs == null)
		{
			qs = newQuestState(talker);
		}
		QuestState reqQs = talker.getQuestState("Q10273_GoodDayToFly");
		QuestState reqQs1 = talker.getQuestState("Q10268_ToTheSeedOfInfinity");
		if ((reqQs != null) && (reqQs.getState() == State.COMPLETED) && (reqQs1 != null) && (reqQs1.getState() == State.COMPLETED))
		{
			long reentertime = InstanceManager.getInstance().getInstanceTime(talker.getObjectId(), 115);
			if (System.currentTimeMillis() >= reentertime)
			{
				if (qs.getInt("cond") == 1)
				{
					return "32603-3.htm";
				}
				if ((talker.getLevel() >= 75) && (talker.getLevel() <= 82))
				{
					return "32603-0.htm";
				}
				else
				{
					return "32603-0a.htm";
				}
			}
			else
			{
				if (qs.getState() == State.STARTED)
				{
					qs.unset("cond");
					qs.exitQuest(true);
				}
				return "32603-0b.htm";
			}
		}
		else
		{
			return "32603-0a.htm";
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState qs = player.getQuestState(getName());
		if (qs == null)
		{
			return null;
		}
		if (event.equalsIgnoreCase("32603-3.htm"))
		{
			if (qs.getInt("cond") != 1)
			{
				qs.set("cond", "1");
				qs.setState(State.STARTED);
			}
		}
		return event;
	}
	
	public static void main(String[] args)
	{
		new Q00694_BreakThroughTheHallOfSuffering(694, Q00694_BreakThroughTheHallOfSuffering.class.getSimpleName(), "Break Through The Hall Of Suffering");
	}
}