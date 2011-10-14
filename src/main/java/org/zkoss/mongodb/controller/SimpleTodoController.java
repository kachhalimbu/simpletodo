/**
 * 
 */
package org.zkoss.mongodb.controller;

import java.text.SimpleDateFormat;
import java.util.UUID;

import org.zkoss.mongodb.dao.TaskDAO;
import org.zkoss.mongodb.model.Task;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

/**
 * @author Ashish
 *
 */
public class SimpleTodoController extends GenericForwardComposer {
	Listbox tasks;
	Textbox name;
	Intbox priority;
	Datebox date;
	
	TaskDAO taskDao = new TaskDAO();
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		tasks.setModel(new ListModelList(taskDao.findAll()));
		tasks.setItemRenderer(new ListitemRenderer() {
			public void render(Listitem item, Object data) throws Exception {
				Task task = (Task) data;
				item.setValue(task);
				new Listcell(task.getName()).setParent(item); 
				new Listcell("" + task.getPriority()).setParent(item); 
				new Listcell(new SimpleDateFormat("yyyy-MM-dd").format(task.getExecutionDate())).setParent(item);
			}
		});
	}
	public void onSelect$tasks(SelectEvent evt) {
		Task task = (Task) tasks.getSelectedItem().getValue();
		name.setValue(task.getName());
		priority.setValue(task.getPriority());
		date.setValue(task.getExecutionDate());
	}
	public void onClick$add(Event evt) {
		Task newTask = new Task(UUID.randomUUID().toString(), name.getValue(), priority.getValue(), date.getValue());
		try {
			taskDao.insert(newTask);
			tasks.setModel(new ListModelList(taskDao.findAll()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onClick$update() {
		Task task = (Task) tasks.getSelectedItem().getValue();
		task.setName(name.getValue());
		task.setPriority(priority.getValue());
		task.setExecutionDate(date.getValue());
		try {
			taskDao.update(task);
			tasks.setModel(new ListModelList(taskDao.findAll()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onClick$delete() {
		Task task = (Task) tasks.getSelectedItem().getValue();
		try {
			taskDao.delete(task);
			tasks.setModel(new ListModelList(taskDao.findAll()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
