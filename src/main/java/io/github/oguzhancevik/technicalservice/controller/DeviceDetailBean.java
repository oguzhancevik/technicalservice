package io.github.oguzhancevik.technicalservice.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import io.github.oguzhancevik.technicalservice.dao.CustomerDao;
import io.github.oguzhancevik.technicalservice.dao.DeviceDao;
import io.github.oguzhancevik.technicalservice.model.entity.Customer;
import io.github.oguzhancevik.technicalservice.model.entity.Device;
import io.github.oguzhancevik.technicalservice.model.entity.User;
import io.github.oguzhancevik.technicalservice.util.Const;
import io.github.oguzhancevik.technicalservice.util.UtilLog;

/**
 * Customer kullanıcısı tarafından yeni cihaz eklemek için veya cihazı
 * güncellemek için kullanılan sınıftır.
 * 
 * @author oguzhan
 */
@ManagedBean(name = "deviceDetailBean")
@ViewScoped
public class DeviceDetailBean {

	@ManagedProperty(value = "#{sessionObject}")
	private SessionObject sessionObject;

	@EJB
	private DeviceDao deviceDao;

	@EJB
	private CustomerDao customerDao;

	private UploadedFile file;

	private Device device;

	/**
	 * Cihaz listesi sayfasından Flash ile bir obje gönderilmiş ise burada kontrol
	 * ediliyor. Dolu ise device değişkenine gelen obje atanıyor.
	 */
	@PostConstruct
	public void init() {
		device = new Device(new Customer(new User()));
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		if (flash.containsKey("model")) {
			device = (Device) flash.get("model");
		}
	}

	/**
	 * @param page
	 *            Yönlendirilecek sayfa adresi
	 */
	public void save(String page) {
		try {
			device.setOwner(customerDao.findByUser(sessionObject.getUser()));
			deviceDao.save(device);
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (Exception e) {
			UtilLog.logToScreen(FacesMessage.SEVERITY_ERROR, "HATA", "Müşteri Kaydedilemedi!");
			UtilLog.log(e);
		}
	}

	/**
	 * 
	 * Sisteme yüklenen cihaz resmi bu metod ile belirlenen yere yüklenir.
	 * 
	 * @param event
	 *            Dosyayı almak için kullanılır.
	 * @throws IOException
	 */
	public void handleFileUpload(FileUploadEvent event) throws IOException {
		file = event.getFile();
		String nextFileName = deviceDao.getNextVal("image_seq") + "." + FilenameUtils.getExtension(file.getFileName());
		Files.write(Paths.get(Const.filePathImage + nextFileName), file.getContents());
		device.setImage(nextFileName);
	}

	public SessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

}
